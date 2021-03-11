package org.sociamedia.producer.utils

import org.sociamedia.producer.generators.DateGenerator.generateRandomNumber

import scala.util.Random

object RandomData {

  def generatePauseContentCreation(minPause: Int, maxPause: Int): Int = generateRandomNumber(minPause, maxPause)

  def getRandomUserId(potentialIds: List[Int]): Int = {
    if(potentialIds.isEmpty) 0
    else new Random().shuffle(potentialIds).head
  }

}
