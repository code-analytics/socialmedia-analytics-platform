name := "producer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  dependencies.typesafeConfig,
  dependencies.avro4s,
  dependencies.avro,
  dependencies.schemaRegClient,
  dependencies.scalaFaker,
  dependencies.kafka,
  dependencies.circe,
  dependencies.avroSerializer,
  dependencies.scalaTest,
  dependencies.fabricator,
  dependencies.akkaActor,
  dependencies.akkaTestKit
)

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven",
  "Fabricator" at "https://dl.bintray.com/biercoff/Fabricator"
)

lazy val dependencies = {

  new {
    val akkaVersion = "2.5.13"

    val typesafeConfig       = "com.typesafe"            % "config"                       % "1.4.0"
    val scalaFaker           = "com.github.stevenchen3"  %% "scala-faker"                 % "0.1.1"
    val kafka                = "org.apache.kafka"        %% "kafka"                       % "2.1.0"
    val avro4s               = "com.sksamuel.avro4s"     % "avro4s-core_2.11"             % "3.0.0-RC2"
    val circe                = "io.circe"                %% "circe-generic"               % "0.9.3"
    val avro                 = "org.apache.avro"         % "avro"                         % "1.7.7"
    val schemaRegClient      = "io.confluent"            % "kafka-schema-registry-client" % "5.2.3"
    val avroSerializer       = "io.confluent"            % "kafka-avro-serializer"        % "3.2.1"
    val scalaTest            = "org.scalatest"           %% "scalatest"                   % "3.2.2" % "test"
    val fabricator           = "com.github.azakordonets" % "fabricator_2.10"              % "1.0.4"
    val akkaActor            = "com.typesafe.akka"       %% "akka-actor"                  % akkaVersion
    val akkaTestKit          = "com.typesafe.akka"       %% "akka-testkit"                % akkaVersion
  }
}


