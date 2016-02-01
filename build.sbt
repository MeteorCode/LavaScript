resolvers += "jitpack" at "https://www.jitpack.io"
resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

lazy val Benchmark = config("bench") extend Test
lazy val scalaMeter = new TestFramework("org.scalameter.ScalaMeterFramework")

lazy val commonSettings = Seq(
    organization := "com.meteorcode"
  , version := "0.0.1"
  , scalaVersion := "2.11.7"
  , libraryDependencies ++= Seq(
        "com.github.dynjs"  % "dynjs"       % "v0.3.1"
      , "org.scalacheck"    %% "scalacheck" % "1.12.2"  % "test"
      , "org.scalatest"     %% "scalatest"  % "2.2.6"   % "test"
      , "com.storm-enroute" %% "scalameter" % "0.7"     % "bench"
      )
  )

lazy val root = Project(
     "core"
   , file(".")
   , settings = Defaults.coreDefaultSettings ++
                              commonSettings ++
                              Seq(name := "lavascript")
  ) configs (
    Benchmark
  ) settings (
    //-- ScalaMeter performance testing settings -----------------------------
    inConfig(Benchmark)(Defaults.testSettings ++ Seq(
        testOptions += Tests.Argument(scalaMeter, "-silent")
      , testFrameworks in Benchmark += scalaMeter
      , logBuffered in Benchmark := false  // ScalaMeter demands these settings
      , parallelExecution in Benchmark := false // due to reasons
    ))
  )
