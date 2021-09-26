package org.socialmedia

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{to_date, to_timestamp, col, unix_timestamp, date_format}

object DataProcessing {

  implicit class AugmentedDataFrame(df: DataFrame) {

    def toFormattedDate(tsColName: String, dateColName: String): DataFrame = {
      df
        .withColumn("timestamp-ts", to_timestamp((col(tsColName))))
        .withColumn(dateColName, date_format(col("timestamp-ts"), "yyyy-MM-dd"))
        .drop(col("timestamp-ts"))
    }
  }

}
