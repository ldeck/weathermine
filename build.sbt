name := "BalloonData"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
)

//scalacOptions ++= Seq("-Ylog-classpath")
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-optimise")
