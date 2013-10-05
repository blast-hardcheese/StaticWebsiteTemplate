name := "MyWebsite"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-io" % "1.3.2",
  jdbc,
  anorm,
  cache
)

play.Project.playScalaSettings
