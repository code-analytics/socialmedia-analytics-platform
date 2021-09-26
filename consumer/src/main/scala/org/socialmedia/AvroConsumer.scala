package org.socialmedia

import io.confluent.kafka.schemaregistry.client.{CachedSchemaRegistryClient, SchemaRegistryClient}
import org.apache.avro.Schema
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.avro.SchemaConverters
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import org.socialmedia.DataProcessing.AugmentedDataFrame

object AvroConsumer extends ConsumerConfig {

  private val schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 128)
  private val kafkaAvroDeserializer = new AvroDeserializer(schemaRegistryClient)

  val deserialize = (bytes: Array[Byte]) => kafkaAvroDeserializer.deserialize(bytes)
  val deserializeUdf = udf(deserialize)

  def streamData(topic: String): Unit = {

    println(s"Streaming data from the $topic topic")

    val avroSchema = schemaRegistryClient.getLatestSchemaMetadata(topic + "-value").getSchema
    val sparkSchema = SchemaConverters.toSqlType(new Schema.Parser().parse(avroSchema))

    val kafkaDataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaUrl)
      .option("failOnDataLoss", "false")
      .option("subscribe", topic)
      .load()
      .withColumn("message", deserializeUdf(col("value")))

      .select(from_json(col("message"), sparkSchema.dataType).alias("parsed_value"))
      .select("parsed_value.*")

      .toFormattedDate("timestamp","date")

    writeStreamToParquet(kafkaDataFrame, "output/data.parquet")

  }

  def writeStreamToParquet(dfToWrite: DataFrame, outputPath: String): Unit = {
    dfToWrite
      .writeStream
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .format("console")
      .outputMode("append")
      /*.format("parquet")
      .outputMode("append")
      .option("path", outputPath)
      .option("checkpointLocation", "checkpoint/filesink_checkpoint")*/
      .start()
      .awaitTermination()
  }

}