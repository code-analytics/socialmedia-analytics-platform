package org.sociamedia.producer.actors

import akka.actor.Actor
import org.sociamedia.common.models.{LikedPicture, LikedVideo, PicturePost, User, VideoPost}
import org.sociamedia.producer.AvroRecordGenerator._
import org.sociamedia.producer.DataProducer.sendToKafka
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo, LikePicture, LikeVideo}
import org.sociamedia.producer.generators.{LikedPictureGenerator, LikedVideoGenerator, PictureGenerator, VideoGenerator}
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

object ContentStore {
  case class AddPicture(user: User)
  case class AddVideo(user: User)
  case class LikePicture(user: User)
  case class LikeVideo(user: User)
}

class ContentStore extends Actor {
  var latestPictureIndex = 0
  var latestVideoIndex = 0
  var latestLikePictureIndex = 0
  var latestLikeVideoIndex = 0

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

    case LikePicture(user) =>
      latestLikePictureIndex = latestLikePictureIndex + 1
      val randomPicIndex = generateRandomNumber(1, latestPictureIndex)
      val likedPicture = LikedPictureGenerator(user.userId, randomPicIndex)
      println(s"User ${user.userId} has like the picture ${likedPicture.pictureId}")
      sendToKafka[LikedPicture]("likedPicture", likedPicture, makeLikedPictureRecord)

    case LikeVideo(user) =>
      latestLikeVideoIndex = latestLikeVideoIndex + 1
      val randomVideoIndex = generateRandomNumber(1, latestLikeVideoIndex)
      val likedVideo = LikedVideoGenerator(user.userId, randomVideoIndex)
      println(s"User ${user.userId} has like the picture ${likedVideo.videoId}")
      sendToKafka[LikedVideo]("likedVideo", likedVideo, makeVideoPictureRecord)
  }
}
