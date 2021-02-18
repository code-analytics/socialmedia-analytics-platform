package org.sociamedia.producer

import akka.actor.Actor
import org.sociamedia.producer.UserActor.CreateUser
import org.sociamedia.producer.DbUtils.writeUserToDb
import org.sociamedia.producer.generators.UserGenerator
import org.sociamedia.producer.generators.UserGenerator.generateUser

object UserActor {
  case object CreateUser
}

class UserActor extends Actor {

  var userId = 0

  override def receive: Receive = {
    case CreateUser =>
      println("User is signing up...")
      val user = generateUser()
      println(user)
      userId = writeUserToDb(user)
  }
}
