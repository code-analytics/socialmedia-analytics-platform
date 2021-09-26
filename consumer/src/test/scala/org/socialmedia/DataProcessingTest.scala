package org.socialmedia

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.apache.spark.sql.{Row, types}
import org.apache.spark.sql.types.{DateType, IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.socialmedia.DataProcessing._

class DataProcessingTest extends AnyFlatSpec with SparkSessionTest with DatasetComparer {

  "A timestamp" should "be converted to the following format yyyy-MM-dd HH.mm" in {

    val inputData = Seq(Row(1629713542))
    val inputSchema = List(
      StructField("timestamp", IntegerType, false)
    )
    val inputDf = spark.createDataFrame(spark.sparkContext.parallelize(inputData),
      StructType(inputSchema))

    val expectedData = Seq(Row(1629713542, "2021-08-23"))
    val expectedSchema = List(
      StructField("timestamp", IntegerType, false),
      StructField("date", StringType, false)
    )
    val expectedDf = spark.createDataFrame(spark.sparkContext.parallelize(expectedData),
      StructType(expectedSchema))

    val actualDf = inputDf.toFormattedDate("timestamp","date")

    assertSmallDatasetEquality(expectedDf, actualDf)

  }

}
