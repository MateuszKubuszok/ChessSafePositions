lazy val root = (project in file(".")).settings(
  name := "Chess Safe Positions",
  version := "0.1.0"
)

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.2.0"