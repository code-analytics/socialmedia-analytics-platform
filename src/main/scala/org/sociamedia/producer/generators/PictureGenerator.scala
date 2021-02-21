package org.sociamedia.producer.generators

import org.sociamedia.common.models.PicturePost

object PictureGenerator {
  def apply(userId: Int, picId: Int): PicturePost = {
    PicturePost(
      pictureId = picId,
      publisherId = userId,
      pictureDate = System.currentTimeMillis / 1000
    )
  }
}
