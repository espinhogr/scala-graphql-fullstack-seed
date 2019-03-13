import com.typesafe.sbt.jse.JsEngineImport.JsEngineKeys
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import com.typesafe.sbt.packager.docker.DockerPlugin
import com.typesafe.sbt.packager.jdkpackager.JDKPackagerPlugin
import com.typesafe.sbt.packager.universal.UniversalPlugin
import com.typesafe.sbt.web.Import._
import com.typesafe.sbt.web.SbtWeb
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayImport
import play.sbt.PlayImport.PlayKeys
import play.sbt.routes.RoutesKeys
import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._
import scalajsbundler.sbtplugin.WebScalaJSBundlerPlugin
import webscalajs.WebScalaJS.autoImport._

object Server {
  private[this] val dependencies = {
    Seq(
      PlayImport.guice,
      "org.sangria-graphql" %% "sangria-play-json" % "1.0.5"
    )
  }

  private[this] lazy val serverSettings = Shared.commonSettings ++ Seq(
    name := Shared.projectId,
    description := Shared.projectName,

    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("stanch", "maven"),
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    ),

    libraryDependencies ++= dependencies,

    // Play
    RoutesKeys.routesGenerator := InjectedRoutesGenerator,
    PlayKeys.externalizeResources := false,
    PlayKeys.devSettings := Seq("play.server.akka.requestTimeout" -> "infinite"),
    PlayKeys.playDefaultPort := Shared.projectPort,
    PlayKeys.playInteractionMode := PlayUtils.NonBlockingInteractionMode,

    // Scala.js
    scalaJSProjects := Seq(WebClient.web_client),

    // Sbt-Web
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node,
    pipelineStages in Assets := Seq(scalaJSPipeline),

    // Fat-Jar Assembly
    assemblyJarName in assembly := Shared.projectId + ".jar",
    assemblyMergeStrategy in assembly := {
      case "play/reference-overrides.conf" => MergeStrategy.concat
      case x => (assemblyMergeStrategy in assembly).value(x)
    },
    mainClass in assembly := Some("Bootstrap"),
    fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value),
  )

  lazy val server = (project in file("server"))
    .enablePlugins(
      SbtWeb, play.sbt.PlayScala, JavaAppPackaging,
      UniversalPlugin, DockerPlugin, JDKPackagerPlugin, WebScalaJSBundlerPlugin
    )
    .settings(serverSettings: _*)
    .settings(Packaging.settings: _*)
    .dependsOn(Shared.sharedJvm, WebClient.web_client, Schema.schema)
}
