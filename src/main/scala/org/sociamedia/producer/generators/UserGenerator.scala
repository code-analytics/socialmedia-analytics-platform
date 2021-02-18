package org.sociamedia.producer.generators

import io.alphash.faker._
import org.sociamedia.common.models.User
import org.sociamedia.producer.generators.DateGenerator.{generateRandomNumber, getBirthday}

object UserGenerator {

  val personGenerator = Person()
  val internetGenerator = Internet()
  val phoneGenerator = Phone()


  def getSignUpTimeStamp(): Long = System.currentTimeMillis / 1000

  def generateUser(): User = {
   User(
      username = internetGenerator.username,
      email = internetGenerator.email,
      phone = phoneGenerator.phoneNumber(),
      firstname = personGenerator.firstNameMale,
      lastname = personGenerator.lastName,
      birthday =  getBirthday(generateRandomNumber(15, 50)),
      signUpDate = getSignUpTimeStamp()
    )
  }

}
