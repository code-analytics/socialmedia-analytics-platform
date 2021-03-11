package org.sociamedia.producer

import org.sociamedia.common.models.User
import scala.util.Random

object DbUtils {

  def writeUserToDb(user: User): Int = Random.nextInt(1000)

}
