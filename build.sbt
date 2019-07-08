import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}


name := """emplish-list"""
organization := "be.adoeraene"

version := "1.0-SNAPSHOT"

val copyFrontendFastOpt = taskKey[File]("Return main process fast compiled file directory.")
lazy val fastOptCompileCopy = taskKey[Unit]("Compile and copy paste projects and generate corresponding json file.")

val copyFrontendFullOpt = taskKey[File]("Return main process full compiled file directory.")
lazy val fullOptCompileCopy = taskKey[Unit]("Compile and copy paste projects, and generate corresponding json file.")


lazy val `shared` = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure)
  .settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.7.5"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.23",
      "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.akka-js" %%% "akkajsactor" % "1.2.5.23",
      "org.akka-js" %%% "akkajstestkit" % "1.2.5.23" % "test",
      "org.scalatest" %%% "scalatest" % "3.0.0" % "test"
    )
  )

lazy val sharedJVM = `shared`.jvm
lazy val sharedJS = `shared`.js

lazy val playProject = (project in file(".")).enablePlugins(PlayScala)
  .dependsOn(sharedJVM)
  .settings(
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "com.typesafe.play" %% "play-slick" % "4.0.2",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
      "org.xerial" % "sqlite-jdbc" % "3.28.0",
      "com.h2database" % "h2" % "1.4.199" % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
    )
  )

lazy val `frontend` = project.in(file("./frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    resolvers += Resolver.bintrayRepo("hmil", "maven"),
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.5",
      "com.raquo" %%% "laminar" % "0.7",
      "fr.hmil" %%% "roshttp" % "2.2.4",
    ),
    scalaJSUseMainModuleInitializer := true,
    copyFrontendFastOpt := {
      (fastOptJS in Compile).value.data
    },
    copyFrontendFullOpt := {
      (fullOptJS in Compile).value.data
    }
  )
  .dependsOn(sharedJS)


val copyPath = os.RelPath("public") / "javascripts"
fastOptCompileCopy := {
  val frontendDirectory = os.Path((copyFrontendFastOpt in `frontend`).value.getAbsoluteFile)
  val base = os.Path(baseDirectory.value.getAbsolutePath)
  os.list(base / copyPath)
    .filter(path => path.endsWith(os.RelPath("frontend-scala.js")) || path.endsWith(os.RelPath("frontend-fastopt.js.map")))
    .foreach(os.remove)
  os.copy(frontendDirectory, base / copyPath / "frontend-scala.js")
  os.copy(
    frontendDirectory / os.up / "frontend-fastopt.js.map",
    base / copyPath / "frontend-fastopt.js.map"
  )
}

