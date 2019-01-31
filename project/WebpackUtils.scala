import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object WebpackUtils {

  val deleteWebpackCache = taskKey[Unit]("Deletes the cache used by webpack if there are changes to the monitored files")

  val wiringTasks = Seq(
    deleteWebpackCache := {
      val webpackMonitored = (Compile / fastOptJS / webpackMonitoredFiles).value
      val webpackCacheDir = (Compile / fastOptJS / webpack / streams).value.cacheDirectory
      val cacheDirectory = streams.value.cacheDirectory / "webpack-cache"
      val logger = streams.value.log

      /*
       * Only deletes the cache if a monitored file has changed.
       *
       * This has been done because webpack does not monitor the output file
       * created by fastOptJS (i.e. web_client-fastopt.js). So when the output file
       * changes, in LibraryTasks.bundle no bundle is created because all the
       * rest of the monitored files stay the same.
       */
      val actionToPerform = FileFunction.cached(cacheDirectory, inStyle = FileInfo.hash) {_ =>
        // Deleting cache directory to make webpack run every time
        // TODO: Improve with webpackDevServer
        val dirToDelete = webpackCacheDir / "fastOptJS-webpack-libraries"
        logger.info("Deleting " + dirToDelete)
        IO.delete(dirToDelete)
        Set()
      }

      actionToPerform(webpackMonitored.toSet)

    },
    (Compile / fastOptJS / webpack) := {
      streams.value.log.info("Running webpack")
      deleteWebpackCache.value
      (Compile / fastOptJS / webpack).value
    }
  )

}
