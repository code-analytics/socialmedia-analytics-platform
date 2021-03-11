package org.socialmedia.utils

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.socialmedia.configuration.AppConfiguration.kafkaConf

object SchemaRegistryUtils {

  val schemaRegistryUrl = kafkaConf.schemaRegistryUrl
  val client = new CachedSchemaRegistryClient(schemaRegistryUrl, Int.MaxValue)

  def getSchemaFromRepo(topic: String): Schema = {
    val schemaAvroString = client.getLatestSchemaMetadata(topic).getSchema
    new Parser().parse(schemaAvroString)
  }

  def writeSchemaToRepo(topic: String, schema: Schema): Unit = client.register(topic, schema)

}
