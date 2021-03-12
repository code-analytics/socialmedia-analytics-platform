package org.socialmedia

import io.confluent.kafka.schemaregistry.client.rest.RestService
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import za.co.absa.abris.avro.functions.from_avro

object StructuredStreamer {

  def streamData(): Unit = {

    val KAFKA_BOOTSTRAP_SERVERS = "localhost:9092"
    val SCHEMA_REGISTRY_URL = "http://localhost:8081"
    val TOPIC = "videoPost"

    val spark: SparkSession = SparkSession.builder()
      .appName("KafkaConsumerAvro")
      .master("local[*]")
      .getOrCreate()


    //    Create REST service to access schema registry and retrieve topic schema (latest)
    val restService = new RestService(SCHEMA_REGISTRY_URL)
    val valueRestResponseSchema = restService.getLatestVersion(TOPIC)
    val jsonSchema = valueRestResponseSchema.getSchema

    val picturePostDf = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS)
      .option("subscribe", TOPIC)
      //.option("startingOffsets", "latest")
      .option("startingoffsets", "earliest")
      .load()
      .select(from_avro(col("value"), jsonSchema) as "data")
      .select("data.*")

    picturePostDf.printSchema()

    //    Stream data to console for testing
    picturePostDf.writeStream
      .option("numRows", 100)
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()

  }

}
