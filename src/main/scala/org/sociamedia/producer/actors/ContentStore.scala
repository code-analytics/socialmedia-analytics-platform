package org.sociamedia.producer.actors

import akka.actor.Actor
import org.sociamedia.common.models.{PicturePost, User, VideoPost}
import org.sociamedia.producer.AvroRecordGenerator.{makePicturePostRecord, makeUserRecord, makeVideoPostRecord}
import org.sociamedia.producer.DataProducer.sendToKafka
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo}
import org.sociamedia.producer.generators.{PictureGenerator, VideoGenerator}

object ContentStore {
  case class AddPicture(user: User)
  case class AddVideo(user: User)
}

class ContentStore extends Actor {
  var latestPictureIndex = 0
  var latestVideoIndex = 0

  override def receive: Receive = {
    case AddPicture(user) =>
      latestPictureIndex = latestPictureIndex + 1
      val picture = PictureGenerator(user.userId, latestPictureIndex)
      println(s"User ${user.userId} has posted the picture $picture")
      sendToKafka[PicturePost]("picturePost", picture, makePicturePostRecord)

    case AddVideo(user) =>
      latestVideoIndex = latestVideoIndex + 1
      val video = VideoGenerator(user.userId, latestVideoIndex)
      println(s"User ${user.userId} has posted the video $video")
      sendToKafka[VideoPost]("videoPost", video, makeVideoPostRecord)
  }
}
