import Deps._

name := """service"""
version := "1.0"
organization := "com.directual"
scalaVersion := "2.11.8"

resolvers ++= Seq(
   ivyLocalRepo, mavenLocalRepo
)

libraryDependencies ++= Seq(
   "com.typesafe.akka" %% "akka-stream" % akkaVersion,
   "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
   "com.directual" %% "spring-context-extension" % "1.0-SNAPSHOT"
)

mainClass in assembly := Some(s"${organization.value}.${name.value}.Main")
assemblyJarName in assembly := s"${name.value}-${version.value}.jar"
