package org.sociamedia.common.models

case class User(
                 username: String,
                 email: String,
                 phone: String,
                 firstname: String,
                 lastname: String,
                 birthday: String,
                 signUpDate: Long
               )
