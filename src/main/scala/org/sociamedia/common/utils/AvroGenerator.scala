package org.sociamedia.common.utils

import com.sksamuel.avro4s.AvroSchema
import org.apache.avro.Schema
import org.sociamedia.common.models.{FriendRequest, FriendRequestAccepted, Friendship, LikedPicture, LikedVideo, PicturePost, VideoPost, User}

import java.io.{File, PrintWriter}
import org.sociamedia.common.utils.SchemaRegistryUtils.writeSchemaToRepo

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
    "friendship" -> AvroSchema[Friendship],
    "friendshipRequest" -> AvroSchema[FriendRequest],
    "friendshipRequestAccepted" -> AvroSchema[FriendRequestAccepted],
    "videoPost" -> AvroSchema[VideoPost],
    "likedVideo" -> AvroSchema[LikedVideo],
    "picturePost" -> AvroSchema[PicturePost],
    "likedPicture" -> AvroSchema[LikedPicture])

  def main(args: Array[String]): Unit = {
    val schemas = makeSchemaMap()
    schemas.foreach(schema => generateUserAvro(schema._1, schema._2))
    schemas.foreach(schema => writeSchemaToRepo(schema._1, schema._2))
  }

}
