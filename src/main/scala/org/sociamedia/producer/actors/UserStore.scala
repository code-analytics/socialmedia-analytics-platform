package org.sociamedia.producer.actors

import akka.actor.Actor
import org.sociamedia.common.models.User
import org.sociamedia.producer.DbUtils.writeUserToDb
import org.sociamedia.producer.actors.UserStore.AddUser
import org.sociamedia.producer.generators.UserGenerator.generateUser

object UserStore {
  case object AddUser
}

class UserStore extends Actor {
  var maxUserId = 0

  override def receive: Receive = {
    case AddUser =>
      maxUserId = maxUserId + 1
      val user = generateUser(maxUserId)
      // Send to Kafka
      sender() ! user
  }
}
