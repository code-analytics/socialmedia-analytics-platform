package org.sociamedia.producer.actors

import akka.actor.Actor
import org.sociamedia.producer.actors.UserStore.AddUser
import org.sociamedia.producer.generators.UserGenerator
import org.sociamedia.producer.DataProducer.sendToKafka
import org.sociamedia.producer.AvroRecordGenerator.makeUserRecord
import org.sociamedia.common.models.User

object UserStore {
  case object AddUser
}

class UserStore extends Actor {
  var maxUserId = 0

  override def receive: Receive = {
    case AddUser =>
      maxUserId = maxUserId + 1
      val user = UserGenerator(maxUserId)
      sendToKafka[User]("user", user, makeUserRecord)
      sender() ! user
  }
}
