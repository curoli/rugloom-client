import java.io.File

name := "rugloom-client"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scala-lang" % "scala-reflect" % scalaVersion.value)

lazy val sbtcp = taskKey[Unit]("sbt-classpath")

sbtcp := {
  val files: Seq[File] = (fullClasspath in Compile).value.files
  val sbtClasspath : String = files.map(x => x.getAbsolutePath).mkString(File.pathSeparator)
  println("Set SBT classpath to 'sbt-classpath' environment variable")
  println(sbtClasspath)
  System.setProperty("rugloom.interpreter.classpath", sbtClasspath)
}

lazy val root = (project in file(".")).enablePlugins(PlayScala)
