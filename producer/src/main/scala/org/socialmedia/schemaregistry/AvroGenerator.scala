package org.socialmedia.schemaregistry

import com.sksamuel.avro4s.AvroSchema
import org.apache.avro.Schema
import org.socialmedia.models._
import org.socialmedia.schemaregistry.SchemaRegistryUtils.writeSchemaToRepo

import java.io.{File, PrintWriter}

object AvroGenerator {

  def generateUserAvro(name: String, schema: Schema): Unit = {
    println(schema.getNamespace())
    val jsonSchema = schema.toString(true)
      .replaceAll("\" :", "\":")
    val writer = new PrintWriter(new File(s"src/main/resources/avro/$name.avsc"))
    writer.write(jsonSchema)
    writer.close()
  }

  def makeSchemaMap(): Map[String, Schema] = Map[String, Schema](
    "user" -> AvroSchema[User],
    "friendshipRequest" -> AvroSchema[FriendRequest],
    "friendshipRequestAccepted" -> AvroSchema[FriendRequestAccepted],
    "videoPost" -> AvroSchema[VideoPost],
    "likedVideo" -> AvroSchema[LikedVideo],
    "picturePost" -> AvroSchema[PicturePost],
    "likedPicture" -> AvroSchema[LikedPicture])

  def main(args: Array[String]): Unit = {
    val schemas = makeSchemaMap()
    //schemas.foreach(schema => generateUserAvro(schema._1, schema._2))
    schemas.foreach(schema => writeSchemaToRepo(schema._1, schema._2))
  }

}
