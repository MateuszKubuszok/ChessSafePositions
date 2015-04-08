lazy val root = (project in file(".")).settings(
  name := "Chess Safe Positions",
  version := "0.1.0"
)

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.2.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "junit" % "junit" % "4.10" % "test"

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil
unmanagedSourceDirectories in Test    := (scalaSource in Test).value :: Nil


