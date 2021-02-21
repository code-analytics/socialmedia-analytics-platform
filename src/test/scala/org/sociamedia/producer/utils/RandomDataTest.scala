package org.sociamedia.producer.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.sociamedia.producer.utils.RandomData.getRandomUserId

class RandomDataTest extends AnyFlatSpec {

  "A random Id" should "be picked from a list of integer" in {
    val inputList = List[Int](1,2,3)
    val pickedNumber = getRandomUserId(inputList)
    println(pickedNumber)
  }

}
