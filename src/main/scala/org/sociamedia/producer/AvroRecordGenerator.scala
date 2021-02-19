package org.sociamedia.producer

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.sociamedia.common.models.{PicturePost, User, VideoPost}

object AvroRecordGenerator {

  def makeUserRecord[T](data: T, avroSchema: Schema): GenericData.Record = {
    val avroRecord = new GenericData.Record(avroSchema)
    val user = data.asInstanceOf[User]
    avroRecord.put("userId", user.userId)
    avroRecord.put("username", user.username)
    avroRecord.put("email", user.email)
    avroRecord.put("phone", user.phone)
    avroRecord.put("firstname", user.firstname)
    avroRecord.put("lastname", user.lastname)
    avroRecord.put("birthday", user.birthday)
    avroRecord.put("signUpDate", user.signUpDate)
    avroRecord.put("country", user.country)
    avroRecord
  }

  def makePicturePostRecord[T](data: T, avroSchema: Schema): GenericData.Record = {
    val avroRecord = new GenericData.Record(avroSchema)
    val picture = data.asInstanceOf[PicturePost]
    avroRecord.put("pictureId", picture.pictureId)
    avroRecord.put("publisherId", picture.publisherId)
    avroRecord.put("pictureDate", picture.pictureDate)
    avroRecord
  }

  def makeVideoPostRecord[T](data: T, avroSchema: Schema): GenericData.Record = {
    val avroRecord = new GenericData.Record(avroSchema)
    val video = data.asInstanceOf[VideoPost]
    avroRecord.put("videoId", video.videoId)
    avroRecord.put("publisherId", video.publisherId)
    avroRecord.put("videoDate", video.videoDate)
    avroRecord.put("videoDuration", video.videoDuration)
    avroRecord
  }

}