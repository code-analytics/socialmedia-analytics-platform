import io.alphash.faker._

import scala.util.Random

case class User(
                username: String,
                firstname: String,
                lastname: String,
                age: Int,
                phone: String
               )

object Producer {

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
    println(record.username, record.age)
  }
}
