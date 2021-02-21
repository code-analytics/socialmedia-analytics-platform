package org.sociamedia.producer.generators

import org.sociamedia.common.models.LikedVideo
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

object LikedVideoGenerator {

  def makeVideoViewingDuration: Int = generateRandomNumber(10, 300)

  def apply(userId: Int, videoId: Int): LikedVideo = {
    LikedVideo(
      videoId = videoId,
      userId = userId,
      userTimeSpent = makeVideoViewingDuration,
      timestamp = System.currentTimeMillis / 1000
    )
  }
}
