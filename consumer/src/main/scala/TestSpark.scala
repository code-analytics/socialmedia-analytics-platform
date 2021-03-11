import org.apache.spark.sql.SparkSession

object TestSpark {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("TestSpark")
      .master("local[*]")
      .getOrCreate()

    sparkSession.read
      .option("header","true")
      .csv("consumer/train.csv")
      .show()
  }

}
