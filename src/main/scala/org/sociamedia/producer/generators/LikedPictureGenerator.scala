package org.sociamedia.producer.generators

import org.sociamedia.common.models.LikedPicture

object LikedPictureGenerator {
  
  def apply(userId: Int, picId: Int): LikedPicture = {
    LikedPicture(
      pictureId = picId,
      userId = userId,
      timestamp = System.currentTimeMillis / 1000
    )
  }

}
