package org.sociamedia.producer.generators

import org.sociamedia.common.models.FriendRequest

object FriendRequestGenerator {

  def apply(senderId: Int, receiverId: Int): FriendRequest = {
    FriendRequest(
      requesterId = senderId,
      receiverId = receiverId,
      timestamp = System.currentTimeMillis / 1000
    )
  }


}
