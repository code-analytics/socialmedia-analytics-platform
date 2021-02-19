package org.sociamedia.producer

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.Properties
import org.sociamedia.common.utils.SchemaRegistryUtils.{getSchemaFromRepo, schemaRegistryUrl}
import org.sociamedia.common.configuration.AppConfiguration.kafkaConf

import scala.util.{Failure, Success, Try}

object DataProducer {

  def getAvroSchema(topic: String): Schema = getSchemaFromRepo(topic)

  def getKafkaProperties(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", kafkaConf.kafkaBrokerUrl)
    props.put("schema.registry.url", schemaRegistryUrl)
    props.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("acks", "1")
    props
  }

  def sendToKafka[T](topic: String, data: T, createRecord: (T, Schema) => GenericData.Record): Unit = {
    val props = getKafkaProperties()
    val producer = new KafkaProducer[String, GenericData.Record](props)
    val key = "1"
    val avroSchema = getAvroSchema(topic)
    val avroRecord = createRecord(data, avroSchema)

    Try {
      val record = new ProducerRecord(topic, key, avroRecord)
      producer.send(record).get()
    }
    match {
      case Success(_) => println(s"Message sent to Kafka")
      case Failure(e) => println(e.getMessage)
    }

  }


}
