package org.socialmedia

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

trait StreamerConf {

  val sparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("NetworkWordCount")

  val streamingContext = new StreamingContext(sparkConf, Seconds(5))

  val brokerId = "localhost:9092"
  val groupId = "GRP1"
  val topics = "picturePost"
  val topicset = topics.split(",").toSet

  val kafkaParams = Map[String, Object](
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokerId,
    ConsumerConfig.GROUP_ID_CONFIG -> groupId,
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer",
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroDeserializer",
    "schema.registry.url" -> "http://localhost:8081",
    "specific.avro.reader" -> "true"
  )

}
