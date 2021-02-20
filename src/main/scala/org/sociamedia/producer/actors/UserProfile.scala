package org.sociamedia.producer.actors

import akka.actor.{Actor, ActorRef, Timers}
import org.sociamedia.producer.actors.UserProfile.{CreateUser, ShareContent}
import org.sociamedia.producer.actors.UserStore.AddUser
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import org.sociamedia.common.models.User
import org.sociamedia.producer.actors.ContentStore.{AddPicture, AddVideo, LikePicture, LikeVideo}
import org.sociamedia.producer.actors.FriendStore.{SendFriendRequest}
import scala.concurrent.Await
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber
import scala.collection.mutable.ListBuffer

object UserProfile {
  case class CreateUser(userStoreRef: ActorRef, contentStoreRef: ActorRef, friendStoreRef: ActorRef)
  case object ShareContent
}

class UserProfile extends Actor with Timers {

  implicit val timeout = Timeout(10 seconds)

  val PostPictureAction = 1
  val PostVideoAction = 2
  val LikePictureAction = 3
  val LikeVideoAction = 4
  val SendFriendRequestAction = 5

  var requestSentToIds = ListBuffer[Int]()

  override def receive: Receive = {
    case CreateUser(userStoreRef, contentStoreRef, friendStoreRef) =>
      val userFuture = userStoreRef ? AddUser
      val user = Await.result(userFuture, timeout.duration).asInstanceOf[User]
      println(s"User ${user.userId} has been created")
      context.become(createContent(user, userStoreRef, contentStoreRef, friendStoreRef), true)
      self ! ShareContent
  }

  def generatePauseContentCreation(): Int = generateRandomNumber(3, 20)

  def getContentAction(): Int = generateRandomNumber(1,5)

  def createContent(user: User, userStoreRef: ActorRef, contentStoreRef: ActorRef,
                    friendStoreRef: ActorRef): Receive = {
    case ShareContent =>
      getContentAction match {
        case PostPictureAction => contentStoreRef ! AddPicture(user)
        case PostVideoAction => contentStoreRef ! AddVideo(user)
        case LikePictureAction => contentStoreRef ! LikePicture(user)
        case LikeVideoAction => contentStoreRef ! LikeVideo(user)
        case SendFriendRequestAction =>
          val receiverIdFuture = friendStoreRef ? SendFriendRequest(user, requestSentToIds.toList, userStoreRef)
          val receiverId = Await.result(receiverIdFuture, timeout.duration).asInstanceOf[Int]
          if(receiverId != 0) requestSentToIds += receiverId
      }
      val pauseDuration = generatePauseContentCreation()
      timers.startSingleTimer("content_creation_timer", ShareContent, pauseDuration seconds)
  }
}
