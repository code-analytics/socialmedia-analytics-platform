package org.socialmedia.schemaregistry

import com.sksamuel.avro4s.AvroSchema
import org.apache.avro.Schema
import org.socialmedia.models._
import org.socialmedia.schemaregistry.SchemaRegistryUtils.writeSchemaToRepo

import java.io.{File, PrintWriter}

object AvroGenerator {

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
    schemas.foreach(schema => writeSchemaToRepo(schema._1, schema._2))
  }

}
