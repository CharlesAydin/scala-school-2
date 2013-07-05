import sbt._
import sbt.Keys._

object SchoolBuild extends Build {

  lazy val all = Project(
    id = "school",
    base = file(".")
  ) aggregate (
    cache, geocode
  )

  lazy val cache = Project(
    id = "school-cache",
    base = file("school-cache")
  ) settings (
    com.twitter.scrooge.ScroogeSBT.newSettings: _*
  ) settings (
    commonSettings: _*
  )

  lazy val geocode = Project(
    id = "school-geocode",
    base = file("school-geocode")
  ) settings (
    resolvers += "spray" at "http://repo.spray.io/",
    libraryDependencies ++= Seq(
      "io.spray" %%  "spray-json" % "1.2.5"
    )
  ) settings (
    commonSettings: _*
  ) dependsOn (
    cache
  )

  val commonSettings = Seq(
    fork in run := true,
    cancelable := true,
    scalaVersion := "2.10.0",
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.8.0",
      "com.twitter" %% "scrooge-runtime" % "3.1.5",
      "com.twitter" %% "finagle-thrift" % "6.4.0",
      "com.twitter" %% "twitter-server" % "1.0.1",
      "org.specs2" %% "specs2" % "2.0" % "test"
    )
  )
}
