package org.sociamedia.common.utils

import com.sksamuel.avro4s.AvroSchema
import org.apache.avro.Schema
import org.sociamedia.common.models.User

import java.io.{File, PrintWriter}

object AvroGenerator {

  def generateUserAvro(): Unit = {
    val schema = userToAvroSchema().toString(true)
      .replaceAll("\" :", "\":")
    val writer = new PrintWriter(new File("src/main/resources/user.avsc"))
    writer.write(schema)
    writer.close()
  }

  private def userToAvroSchema(): Schema = AvroSchema[User]

}
