import sbt._

name := "myob"
version := "0.1"
scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .settings(name := "myob")
  .settings(
    inThisBuild(
      Seq(
        organization := "com.myob.employee.payslip",
        scalaVersion := "2.12.8",
        scalacOptions ++= Seq(
          "-feature",
          "-deprecation",
          "-unchecked",
          "-Xcheckinit",
          "-Xlint",
          "-Xverify",
          "-Yno-adapted-args",
          "-encoding", "utf8"
        ),
        Test / coverageEnabled := true,
        Compile / coverageEnabled := false
      )
    )
  )
  .settings(Test / parallelExecution := false)
  .settings(libraryDependencies ++= (Dependencies.compile ++ Dependencies.test))
  .settings(
    // Assembly settings
    buildInfoPackage := "com.myob.employee.payslip",
    assembly / test  := {},
    assembly / assemblyJarName  := name.value + "-assembly.jar"
  )
