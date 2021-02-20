package org.sociamedia.producer.actors

import akka.actor.{Actor, ActorRef}
import akka.pattern.ask
import akka.util.Timeout
import org.sociamedia.common.models.{FriendRequest, User}
import org.sociamedia.producer.AvroRecordGenerator.{makeFriendRequestRecord}
import org.sociamedia.producer.DataProducer.sendToKafka
import org.sociamedia.producer.actors.FriendStore.SendFriendRequest
import org.sociamedia.producer.actors.UserStore.GetNumUsers
import org.sociamedia.producer.generators.FriendRequestGenerator
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.Random

object FriendStore {
  case class SendFriendRequest(user: User, requestSentToIds: List[Int], userStoreRef: ActorRef)
  case class AcceptFriendRequest(user: User)
}

class FriendStore extends Actor {

  implicit val timeout = Timeout(10 seconds)

  def getRandomUserId(potentialIds: List[Int]): Int = {
    if(potentialIds.isEmpty) 0
    else new Random().shuffle(potentialIds).head
  }

  override def receive: Receive = {
    case SendFriendRequest(user, requestSentToIds, userStoreRef) =>
      /* Make a list of all ids of the users who are not yet friends with the requester  */
      val potentialFriendsFuture = userStoreRef ? GetNumUsers
      val maxNumUsers = Await.result(potentialFriendsFuture, timeout.duration).asInstanceOf[Int]
      println(s"User ${user.userId} asks the max number of users --> $maxNumUsers")
      val newPotentialFriends = (1 to maxNumUsers).toList
        .filter(user => !requestSentToIds.contains(user))

      val newFriendId = getRandomUserId(newPotentialFriends)

      if(newFriendId != 0) {
        val friendRequest = FriendRequestGenerator(user.userId, newFriendId)
        sendToKafka[FriendRequest]("friendshipRequest", friendRequest, makeFriendRequestRecord)
        sender() ! newFriendId
      }
      sender() ! 0
  }
}
