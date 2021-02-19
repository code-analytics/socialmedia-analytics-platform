package org.sociamedia.producer.generators

import org.scalatest.flatspec.AnyFlatSpec
import org.sociamedia.producer.generators.DateGenerator._
import java.text.SimpleDateFormat
import org.sociamedia.producer.generators.UserGenerator._
import org.sociamedia.producer.generators.CountryUsers.countries

class UserGeneratorTest extends AnyFlatSpec {

  "A number" should "be randomly picked between 1 and 5" in {
    val startNumber = 1
    val endNumber = 5
    val draw1 = generateRandomNumber(startNumber, endNumber)
    val draw2 = generateRandomNumber(startNumber, endNumber)
    val draw3 = generateRandomNumber(startNumber, endNumber)
    assert(draw1 > (startNumber-1) && draw1 < (endNumber+1))
    assert(draw2 > (startNumber-1) && draw2 < (endNumber+1))
    assert(draw3 > (startNumber-1) && draw3 < (endNumber+1))
  }

  "A user of 30 years" should "have his birthday between 1990/01/01 and 1990/12/31" in {
    val age = 30
    val randomBirthday = generateBirthdayDate(age)
    val minExpectedDate = new SimpleDateFormat( "yyyyMMdd" ).parse( "19900101" )
    val maxExpectedDate = new SimpleDateFormat( "yyyyMMdd" ).parse( "19901231" )
    assert(
      randomBirthday.equals(minExpectedDate)
        || randomBirthday.equals(maxExpectedDate)
        || (randomBirthday.after(minExpectedDate) && randomBirthday.before(maxExpectedDate))
    )
  }

  "The date object" should "be have the following yyyyMMdd" in {
    val date = new SimpleDateFormat( "yyyyMMdd" ).parse( "19900101" )
    println(date)
    val formattedDate = formatDate(date)
    println(formattedDate)
    val expectedFormattedDate = "1990-01-01"
    assert(formattedDate == expectedFormattedDate)
  }

  "a country name" should "be randomly picked from the country Map object" in {
    val country = getCountry()
    val listOfCountries = countries.map(_._1).toList
    assert(listOfCountries.contains(country))
  }

}
