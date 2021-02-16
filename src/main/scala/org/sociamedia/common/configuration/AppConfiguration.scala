package org.sociamedia.common.configuration

import com.typesafe.config.ConfigFactory

object AppConfiguration {

  case class KafkaConf(kafkaBrokerUrl: String, schemaRegistryUrl: String)

  private val configFile = ConfigFactory.load()

  val kafkaConf = KafkaConf(
    configFile.getString("kafka.kafkaBrokerUrl"),
    configFile.getString("kafka.schemaRegistryUrl")
  )

}
