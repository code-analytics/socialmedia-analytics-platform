package org.sociamedia.producer.actors

import akka.actor.{Actor, ActorRef, Timers}
import org.sociamedia.producer.actors.UserProfile.{CreateUser, ShareContent}
import org.sociamedia.producer.actors.UserStore.AddUser
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import org.sociamedia.common.models.User
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo}
import scala.concurrent.Await
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

object UserProfile {
  case class CreateUser(userStoreRef: ActorRef, contentStoreRef: ActorRef)
  case object ShareContent
  case object Content
}

class UserProfile extends Actor with Timers {

  implicit val timeout = Timeout(10 seconds)

  override def receive: Receive = {
    case CreateUser(userStoreRef, contentStoreRef) =>
      val userFuture = userStoreRef ? AddUser
      val user = Await.result(userFuture, timeout.duration).asInstanceOf[User]
      println(s"User ${user.userId} has been created")
      context.become(createContent(user, contentStoreRef), true)
      self ! ShareContent
  }

  def generatePauseContentCreation(): Int = generateRandomNumber(1, 5)

  def createContent(user: User, contentStoreRef: ActorRef): Receive = {
    case ShareContent =>
      generateRandomNumber(1,2) match {
        case 1 => contentStoreRef ! AddPicture(user)
        case 2 => contentStoreRef ! AddVideo(user)
      }
      val pauseDuration = generatePauseContentCreation()
      timers.startSingleTimer("content_creation_timer", ShareContent, pauseDuration seconds)
  }
}
