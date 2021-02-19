package org.sociamedia.producer

import akka.actor.{ActorRef, ActorSystem, Props}
import org.sociamedia.producer.actors.{ContentStore, UserProfile, UserStore}
import org.sociamedia.producer.actors.UserProfile.CreateUser
import org.sociamedia.producer.generators.DateGenerator.generateTimeBeforeCreate

import scala.concurrent.duration._

object Publisher {

  case class UserActorConfig(ref: ActorRef, name: String, timeBeforeCreate: Int)

  val system = ActorSystem("socialmedia_data_generator")
  import system.dispatcher

  def makeUserActorConfig(totalUsers: Int): List[UserActorConfig] = {
    (1 to totalUsers).toList.map { userId =>
      val userActorName = s"user_$userId"
      val userActor = system.actorOf(Props[UserProfile], userActorName)
      UserActorConfig(userActor, userActorName, generateTimeBeforeCreate(0.001))
    }
  }

  def main(args: Array[String]): Unit = {
   val usersConfig = makeUserActorConfig(5)

    val userStore = system.actorOf(Props[UserStore], "user_store")
    val contentStore = system.actorOf(Props[ContentStore], "content_store")

    usersConfig.foreach { user =>
      system.scheduler.scheduleOnce(user.timeBeforeCreate seconds,
        user.ref, CreateUser(userStore, contentStore))
    }
  }
}
