package org.sociamedia.producer.actors

import akka.actor.{Actor, ActorRef, Timers}
import org.sociamedia.producer.actors.UserProfile.{CreateUser, ShareContent}
import org.sociamedia.producer.actors.UserStore.AddUser
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import org.sociamedia.common.models.User
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo, LikePicture, LikeVideo}
import scala.concurrent.Await
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

object UserProfile {
  case class CreateUser(userStoreRef: ActorRef, contentStoreRef: ActorRef)
  case object ShareContent
}

class UserProfile extends Actor with Timers {

  implicit val timeout = Timeout(10 seconds)

  val PostPictureAction = 1
  val PostVideoAction = 2
  val LikePictureAction = 3
  val LikeVideoAction = 4

  override def receive: Receive = {
    case CreateUser(userStoreRef, contentStoreRef) =>
      val userFuture = userStoreRef ? AddUser
      val user = Await.result(userFuture, timeout.duration).asInstanceOf[User]
      println(s"User ${user.userId} has been created")
      context.become(createContent(user, contentStoreRef), true)
      self ! ShareContent
  }

  def generatePauseContentCreation(): Int = generateRandomNumber(1, 120)

  def getContentAction(): Int = generateRandomNumber(1,4)

  def createContent(user: User, contentStoreRef: ActorRef): Receive = {
    case ShareContent =>
      getContentAction match {
        case PostPictureAction => contentStoreRef ! AddPicture(user)
        case PostVideoAction => contentStoreRef ! AddVideo(user)
        case LikePictureAction => contentStoreRef ! LikePicture(user)
        case LikeVideoAction => contentStoreRef ! LikeVideo(user)
      }
      val pauseDuration = generatePauseContentCreation()
      timers.startSingleTimer("content_creation_timer", ShareContent, pauseDuration seconds)
  }
}
