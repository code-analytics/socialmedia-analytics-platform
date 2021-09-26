name := "consumer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  dependencies.sparkCore,
  dependencies.sparkSql,
  dependencies.sparkStreaming,
  dependencies.sparkStreamingKafka,
  dependencies.kafkaClient,
  dependencies.avroSerializer,
  dependencies.avroBridge,
  dependencies.sparkKafkaSql,
  dependencies.scalaTest,
  dependencies.sparkFastTests,
  dependencies.sparkDaria
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7.1"

lazy val dependencies = {

  new {
    val sparkV = "2.4.0"
    val avroSerializer       = "io.confluent"            % "kafka-avro-serializer"        % "3.2.1"
    val scalaTest            = "org.scalatest"           %% "scalatest"                   % "3.2.2" % "test"
    val sparkCore            = "org.apache.spark"        %% "spark-core"                  % sparkV
    val kafkaClient          = "org.apache.kafka"        % "kafka-clients"                % "2.7.0"
    val sparkSql             = "org.apache.spark"        %% "spark-sql"                   % sparkV
    val sparkStreaming       = "org.apache.spark"        %% "spark-streaming"             % sparkV
    val sparkStreamingKafka  = "org.apache.spark"        %% "spark-streaming-kafka-0-10"  % sparkV
    val sparkKafkaSql        = "org.apache.spark"        %% "spark-sql-kafka-0-10"        % "2.4.4"
    val avroBridge           = "za.co.absa"              % "abris_2.11"                   % "4.2.0"
    val sparkFastTests       = "com.github.mrpowers"     %% "spark-fast-tests"            % "0.23.0" % "test"
    val sparkDaria           = "com.github.mrpowers"     %% "spark-daria"                 % "0.39.0"

  }
}

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.1.0"

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven",
  "Fabricator" at "https://dl.bintray.com/biercoff/Fabricator"
)

