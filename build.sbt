name := "rugloom-client"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies +=  "org.scala-lang" % "scala-compiler" % "2.11.7"

libraryDependencies +=  "org.scala-lang" % "scala-reflect" % "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
