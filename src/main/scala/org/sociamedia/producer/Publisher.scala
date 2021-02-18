package org.sociamedia.producer

import io.alphash.faker.Person
import org.sociamedia.common.models.User
import org.sociamedia.producer.AvroRecordGenerator.makeUserRecord
import org.sociamedia.producer.DataProducer.sendUserToKafka

object Publisher {

  def generateRecord(): User = {
    val personGenerator = Person()
    User(
      firstname = personGenerator.firstNameMale,
      lastname = personGenerator.lastName
    )
  }

  def main(args: Array[String]): Unit = {
      val record = generateRecord()
      sendUserToKafka("users", record, makeUserRecord[User])
  }
}
