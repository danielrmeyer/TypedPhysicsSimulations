ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "TypedPhysicsSimulations"
  )

libraryDependencies += "com.manyangled" %% "coulomb-core" % "0.8.0"
libraryDependencies += "com.manyangled" %% "coulomb-units" % "0.8.0"