resolvers += "jitpack" at "https://www.jitpack.io"
resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

organization := "com.meteorcode"

version := "0.0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    "com.github.dynjs"  % "dynjs"       % "v0.3.1"
  , "org.scalacheck"    %% "scalacheck" % "1.12.2"  % "test"
  , "org.scalatest"     %% "scalatest"  % "2.2.6"   % "test"
  , "com.storm-enroute" %% "scalameter" % "0.7"     % "bench"
  )

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered in Test := false
