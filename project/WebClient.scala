import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._
import webscalajs.ScalaJSWeb

object WebClient {

  val slinkyVer = "0.5.2"
  val reactVer = "16.5.2"
  val apolloScalaJsVer = "0.6.0"


  private[this] val clientSettings = 
    Shared.commonSettings ++ WebpackUtils.wiringTasks ++ GraphqlUtils.generateTasks ++ Seq(
    scalaJSUseMainModuleInitializer := true, // Starts scalajs from a main function
    mainClass in Compile := Some("com.mypackage.Bootstrap"),

    resolvers += "Apollo Bintray" at "https://dl.bintray.com/apollographql/maven/",

    libraryDependencies ++= Seq(
      "me.shadaj" %%% "slinky-web" % slinkyVer, // React DOM, HTML and SVG tags
      "me.shadaj" %%% "slinky-hot" % slinkyVer, // Hot loading, requires react-proxy package
      "me.shadaj" %%% "slinky-scalajsreact-interop" % slinkyVer, // Interop with japgolly/scalajs-react
      "com.apollographql" %%% "apollo-scalajs-core" % apolloScalaJsVer,
      "com.apollographql" %%% "apollo-scalajs-react" % apolloScalaJsVer,
      "org.sangria-graphql" %% "sangria-circe" % "1.1.0"
    ),
    Compile / npmDependencies ++= Seq(
      "react" -> reactVer,
      "react-dom" -> reactVer,
      "react-proxy" -> "1.1.8",
      "apollo-boost" -> "0.1.16",
      "apollo-cache-inmemory" -> "1.4.3",
      "react-apollo" -> "2.2.2",
      "graphql-tag" -> "2.10.0",
      "graphql" -> "14.0.2",
      "antd" -> "3.13.0"
    ),

    Compile / npmDevDependencies ++= Seq(
      "file-loader" -> "2.0.0",
      "style-loader" -> "0.23.1",
      "css-loader" -> "1.0.0",
      "copy-webpack-plugin" -> "4.5.4",
      "clean-webpack-plugin" -> "1.0.0",
      "webpack-merge" -> "4.1.4",
      "apollo" -> "2.5.3"
    ),

    webpack / version := "4.21.0",
    startWebpackDevServer / version := "3.1.14",

    webpackResources := baseDirectory.value / "webpack" * "*",
    fastOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
    fullOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),

    fastOptJS / webpackBundlingMode := BundlingMode.LibraryOnly(),

    scalacOptions ++= Seq("-P:scalajs:sjsDefinedByDefault", "-P:scalajs:suppressExportDeprecations"),

    GraphqlUtils.npmUpdateTask := (Compile / npmInstallDependencies).value,

  )

  lazy val web_client = (project in file("web_client"))
    .settings(clientSettings: _*)
//    .settings(addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS"): _*)
    .enablePlugins(ScalaJSPlugin, ScalaJSWeb, ScalaJSBundlerPlugin)
    .dependsOn(Shared.sharedJs, Schema.schema)
}


