package org.sociamedia.producer

import io.alphash.faker.{Internet, Person, Phone}
import org.sociamedia.common.models.User

import scala.util.Random

object Publisher {

  def generateRecord(): User = {
    val personGenerator = Person()
    User(
      username = Internet().username,
      firstname = personGenerator.firstNameMale,
      lastname = personGenerator.lastName,
      age = 18 + Random.nextInt(90),
      phone = Phone().phoneNumber()
    )
  }

  def main(args: Array[String]): Unit = {
    val record = generateRecord()
    println(s"${record.firstname} ${record.lastname}")
  }
}
