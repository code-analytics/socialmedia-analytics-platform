package org.socialmedia

import org.socialmedia.models.User
import scala.util.Random

object DbUtils {

  def writeUserToDb(user: User): Int = Random.nextInt(1000)

}
