name := "liqui-sample"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++=
    akka("2.3.4") ++
    spray("1.3.1") ++
    dbDeps ++
  Seq(
    "org.scala-lang"            %   "scala-reflect"       % "2.11.2",
    "org.json4s"                %%  "json4s-jackson"      % "3.2.10",
    "ch.qos.logback"            %   "logback-classic"     % "1.1.2",
    "org.scalatest"             %%  "scalatest"           % "2.2.0"     % "test"
  )

def akka(v: String) = Seq(
  "com.typesafe.akka" %% "akka-actor"   % v,
  "com.typesafe.akka" %% "akka-remote"  % v,
  "com.typesafe.akka" %% "akka-slf4j"   % v,
  "com.typesafe.akka" %% "akka-testkit" % v % "test"
)

def spray(v : String) = Seq(
  "io.spray"            %%   "spray-can"     % v,
  "io.spray"            %%   "spray-routing" % v,
  "io.spray"            %%   "spray-testkit" % v  % "test"
)

def dbDeps =  Seq(
  "postgresql"              %  "postgresql"                % "9.1-901.jdbc4",
  "org.liquibase"           % "liquibase-core"            % "3.2.2",
  "com.typesafe.slick"      %%  "slick"                    % "2.1.0"
)
