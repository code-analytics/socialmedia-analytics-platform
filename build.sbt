name := "SocialMediaP"
version := "0.1"
scalaVersion := "2.11.8"

lazy val global = project
  .in(file("."))
  .aggregate(
    common,
    producer,
    consumer
  )

lazy val common = project
  .settings(
    name := "common",
    settings,
    libraryDependencies ++= commonDependencies
  )

lazy val producer = project
  .settings(
    name := "producer",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.scalaFaker,
      dependencies.kafka,
      dependencies.circe,
      dependencies.avroSerializer,
      dependencies.scalaTest,
      dependencies.fabricator,
      dependencies.akkaActor,
      dependencies.akkaTestKit
    )
  )
  .dependsOn(
    common
  )

lazy val consumer = project
  .settings(
    name := "consumer",
    settings,
    libraryDependencies ++= Seq(
      dependencies.sparkCore,
      dependencies.sparkSql,
      dependencies.sparkStreaming,
      dependencies.sparkStreamingKafka,
      dependencies.kafkaClient,
      dependencies.avroSerializer,
      dependencies.avroBridge,
      dependencies.sparkKafkaSql
    ),
    dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7.1"
  )


lazy val dependencies = {

  new {
    val sparkV = "2.4.0"
    val akkaVersion = "2.5.13"

    val typesafeConfig       = "com.typesafe"            % "config"                       % "1.4.0"
    val scalaFaker           = "com.github.stevenchen3"  %% "scala-faker"                 % "0.1.1"
    val kafka                = "org.apache.kafka"        %% "kafka"                       % "2.1.0"
    val avro4s               = "com.sksamuel.avro4s"     %% "avro4s-core"                 % "4.0.4"
    val circe                = "io.circe"                %% "circe-generic"               % "0.9.3"
    val avro                 = "org.apache.avro"         % "avro"                         % "1.7.7"
    val schemaRegClient      = "io.confluent"            % "kafka-schema-registry-client" % "5.2.3"
    val avroSerializer       = "io.confluent"            % "kafka-avro-serializer"        % "3.2.1"
    val scalaTest            = "org.scalatest"           %% "scalatest"                   % "3.2.2" % "test"
    val fabricator           = "com.github.azakordonets" % "fabricator_2.10"              % "1.0.4"
    val akkaActor            = "com.typesafe.akka"       %% "akka-actor"                  % akkaVersion
    val akkaTestKit          = "com.typesafe.akka"       %% "akka-testkit"                % akkaVersion
    val sparkCore            = "org.apache.spark"        %% "spark-core"                  % sparkV
    val kafkaClient          = "org.apache.kafka"        % "kafka-clients"                % "2.7.0"
    val sparkSql             = "org.apache.spark"        %% "spark-sql"                   % sparkV
    val sparkStreaming       = "org.apache.spark"        %% "spark-streaming"             % sparkV
    val sparkStreamingKafka  = "org.apache.spark"        %% "spark-streaming-kafka-0-10"  % sparkV
    val sparkKafkaSql        = "org.apache.spark"        %% "spark-sql-kafka-0-10"        % "2.4.4"
    val avroBridge           = "za.co.absa"              % "abris_2.12"                   % "4.1.0"
  }
}

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.1.0"

lazy val commonDependencies = Seq(
  dependencies.typesafeConfig,
  dependencies.avro4s,
  dependencies.avro,
  dependencies.schemaRegClient
)

lazy val settings = commonSettings

lazy val commonSettings = Seq(
  resolvers ++= Seq(
    "Confluent" at "https://packages.confluent.io/maven",
    "Fabricator" at "https://dl.bintray.com/biercoff/Fabricator"
  )
)
