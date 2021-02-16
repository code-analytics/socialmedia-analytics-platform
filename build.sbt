name := "ECommerceStreaming"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.stevenchen3" %% "scala-faker" % "0.1.1",
  "org.apache.kafka" %% "kafka" % "2.1.0",
  "com.chuusai" % "shapeless_2.11" % "2.3.3",
  "com.sksamuel.avro4s" % "avro4s-core_2.11" % "3.0.0-RC2",
  "io.circe" %% "circe-generic" % "0.9.3",
  "org.apache.avro"  %  "avro"  %  "1.7.7",
  "io.confluent" % "kafka-schema-registry-client" % "5.2.3",
  "io.confluent" % "kafka-avro-serializer" % "3.2.1"
//  "org.apache.spark" %% "spark-sql" % "2.4.4"
)

resolvers += "Confluent" at "https://packages.confluent.io/maven"
