val Http4sVersion     = "0.21.1"
val CirceVersion      = "0.13.0"
val Log4CatsVersion   = "1.0.1"
val DoobieVersion     = "0.8.8"
val PureConfigVersion = "0.12.3"

lazy val root = (project in file("."))
  .settings(
    organization := "slouc",
    name := "myproject",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"            %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"            %% "http4s-circe"        % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"          % Http4sVersion,
      "io.circe"              %% "circe-generic"       % CirceVersion,
      "io.chrisdavenport"     %% "log4cats-slf4j"      % Log4CatsVersion,
      "org.slf4j"             % "slf4j-simple"         % "1.7.5",
      "org.tpolecat"          %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"          %% "doobie-hikari"       % DoobieVersion,
      "org.tpolecat"          %% "doobie-postgres"     % DoobieVersion,
      "com.github.pureconfig" %% "pureconfig"          % PureConfigVersion
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
