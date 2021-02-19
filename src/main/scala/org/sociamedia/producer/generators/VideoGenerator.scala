package org.sociamedia.producer.generators

import org.sociamedia.common.models.VideoPost
import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

object VideoGenerator {

  def makeVideoDuration: Int = generateRandomNumber(10, 300)

  def apply(userId: Int, videoId: Int): VideoPost = {
    VideoPost(
      videoId = videoId,
      publisherId = userId,
      videoDate = System.currentTimeMillis / 1000,
      videoDuration = makeVideoDuration
    )
  }
}
