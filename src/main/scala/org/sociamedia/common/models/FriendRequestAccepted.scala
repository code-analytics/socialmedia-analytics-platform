package org.sociamedia.common.models

case class FriendRequestAccepted(
                                  accepterId: Int,
                                  requesterId: Int,
                                  timestamp: Long
                                )
