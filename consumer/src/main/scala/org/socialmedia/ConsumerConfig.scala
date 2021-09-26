package org.socialmedia

import org.apache.spark.sql.SparkSession


trait ConsumerConfig {

  val kafkaUrl = "http://localhost:9092"
  val schemaRegistryUrl = "http://localhost:8081"
  val topicsToConsume = List[String]("friendshipRequest", "user","picturePost","friendshipRequestAccepted","videoPost",
    "likedPicture","likedVideo")

  val spark = SparkSession
    .builder
    .appName("ConfluentConsumer")
    .master("local[*]")
    .getOrCreate()

}
