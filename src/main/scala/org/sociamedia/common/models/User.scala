package org.sociamedia.common.models

case class User(
                 userId: Int,
                 username: String,
                 email: String,
                 phone: String,
                 firstname: String,
                 lastname: String,
                 birthday: String,
                 signUpDate: Long,
                 country: String
               )
