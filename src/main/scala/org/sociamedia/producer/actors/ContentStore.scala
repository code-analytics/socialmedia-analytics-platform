package org.sociamedia.producer.actors

import akka.actor.Actor
import org.sociamedia.common.models.User
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo}

object ContentStore {
  case class AddPicture(user: User)
  case class AddVideo(user: User)
}

class ContentStore extends Actor {
  var latestPictureIndex = 0
  var latestVideoIndex = 0

  override def receive: Receive = {
    case AddPicture(user) =>
      // generatePicture()
      latestPictureIndex = latestPictureIndex + 1
      println(s"User ${user.userId} has posted the picture ${latestPictureIndex}")
      // sendtoKafka

    case AddVideo(user) =>
      // generateVideo()
      latestVideoIndex = latestVideoIndex + 1
      println(s"User ${user.userId} has posted the video ${latestVideoIndex}")
    // sendtoKafka
  }
}
