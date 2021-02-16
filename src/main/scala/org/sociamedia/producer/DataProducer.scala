package org.sociamedia.producer

import org.apache.avro.generic.{GenericData}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.sociamedia.common.models.User
import java.util.Properties
import org.sociamedia.common.utils.SchemaRegistryUtils.{getSchemaFromRepo, schemaRegistryUrl}
import org.sociamedia.common.configuration.AppConfiguration.kafkaConf
import scala.util.{Try,Success,Failure}

object DataProducer {

  def sendUserToKafka(topic: String, user: User): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", kafkaConf.kafkaBrokerUrl)
    props.put("schema.registry.url", schemaRegistryUrl)
    props.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("acks", "1")

    val producer = new KafkaProducer[String, GenericData.Record](props)

    val key = "1"

    val valueSchemaAvro = getSchemaFromRepo(topic+"-value")
    val avroRecord = new GenericData.Record(valueSchemaAvro)

    avroRecord.put("firstname", user.firstname)
    avroRecord.put("lastname", user.lastname)

    Try {
      val record = new ProducerRecord("users", key, avroRecord)
      val ack = producer.send(record).get()
      println(s"ack: $ack")
    }
    match {
      case Success(_) => println(s"Message sent to Kafka")
      case Failure(e) => println(e.getMessage)
    }

  }


}
