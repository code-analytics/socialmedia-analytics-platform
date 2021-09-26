package org.socialmedia

import org.apache.spark.sql.SparkSession

trait SparkSessionTest {

  val spark = SparkSession
    .builder
    .appName("ConsumerTest")
    .master("local[*]")
    .getOrCreate()
}
