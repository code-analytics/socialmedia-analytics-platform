package org.socialmedia.actors

import akka.actor.Actor
import org.socialmedia.AvroRecordGenerator.makeUserRecord
import org.socialmedia.DataProducer.sendToKafka
import org.socialmedia.actors.UserStore.{AddUser, GetNumUsers}
import org.socialmedia.generators.UserGenerator
import org.socialmedia.models.User

object UserStore {
  case object AddUser
  case object GetNumUsers
}

class UserStore extends Actor {
  var maxUserId = 0

  override def receive: Receive = {
    case AddUser =>
      maxUserId = maxUserId + 1
      val user = UserGenerator(maxUserId)
      sendToKafka[User]("user", user, makeUserRecord)
      sender() ! user

    case GetNumUsers => sender() ! maxUserId
  }
}
