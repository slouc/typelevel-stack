val Http4sVersion  = "0.21.1"
val CirceVersion   = "0.13.0"
val LogbackVersion = "1.2.3"
val DoobieVersion = "0.8.8"

lazy val root = (project in file("."))
  .settings(
    organization := "slouc",
    name := "myproject",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"     %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"     %% "http4s-circe"        % Http4sVersion,
      "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
      "io.circe"       %% "circe-generic"       % CirceVersion,
      "ch.qos.logback" % "logback-classic"      % LogbackVersion,
      "org.tpolecat"   %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"   %% "doobie-hikari"       % DoobieVersion,
      "org.tpolecat"   %% "doobie-postgres"     % DoobieVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)
