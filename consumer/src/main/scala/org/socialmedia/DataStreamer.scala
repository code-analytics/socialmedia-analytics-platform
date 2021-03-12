package org.socialmedia

import org.apache.avro.generic.GenericRecord
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

object DataStreamer extends StreamerConf {

  def streamData(): Unit = {
    val message = KafkaUtils.createDirectStream[String, GenericRecord](
      streamingContext,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, GenericRecord](topicset, kafkaParams)
    ).map(cr => cr.value)

    message.print()
    streamingContext.start()
    streamingContext.awaitTermination()
  }

}
