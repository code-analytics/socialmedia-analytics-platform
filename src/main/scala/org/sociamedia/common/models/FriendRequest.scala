package org.sociamedia.common.models

case class FriendRequest(
                          requesterId: Int,
                          receiverId: Int,
                          timestamp: Long
                        )
