lazy val scalatraVersion = "2.4.0"

lazy val root = (project in file(".")).settings(
  organization := "wivern",
  name := "eshop",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    "org.scalatra"      %% "scalatra"          % scalatraVersion,
    "org.scalatra"      %% "scalatra-scalate"  % scalatraVersion,
    "org.scalatra"      %% "scalatra-auth"     % scalatraVersion,
    "org.scalatra"      %% "scalatra-specs2"   % scalatraVersion    % "test",
    "org.squeryl"       % "squeryl_2.11"       % "0.9.5-7",
    "com.h2database"    % "h2"                 % "1.4.181",
    "com.mchange"       % "c3p0"               % "0.9.5.1",
    "com.github.scribejava" % "scribejava-apis" % "2.2.2",
    "ch.qos.logback"    %  "logback-classic"   % "1.1.3"            % "runtime",
    "org.eclipse.jetty" %  "jetty-webapp"      % "9.2.14.v20151106" % "container;compile",
    "javax.servlet"     %  "javax.servlet-api" % "3.1.0"            % "provided"
  )
).settings(jetty(): _*)
//Seq(webSettings :_*)
    