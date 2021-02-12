import io.alphash.faker._
import models.User
import DataProducer.writeToKafka

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
    writeToKafka("customer_record","Black Belt - 25€")
  }
}
