package org.socialmedia.generators

import org.socialmedia.models.PicturePost

object PictureGenerator {
  def apply(userId: Int, picId: Int): PicturePost = {
    PicturePost(
      pictureId = picId,
      publisherId = userId,
      pictureDate = System.currentTimeMillis / 1000
    )
  }
}
