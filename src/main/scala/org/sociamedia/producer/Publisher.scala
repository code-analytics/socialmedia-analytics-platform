package org.sociamedia.producer

import akka.actor.{ActorSystem, Props}
import org.sociamedia.producer.UserActor.CreateUser

object Publisher {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("socialmedia_data_generator")
    for(actorId <- 1 to 100) {
      val user = actorSystem.actorOf(Props[UserActor], s"actor$actorId")
      user ! CreateUser
    }

  }
}
