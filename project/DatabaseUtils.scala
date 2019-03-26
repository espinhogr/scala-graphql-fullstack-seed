import com.typesafe.config.ConfigFactory
import sbt.Keys._
import sbt._

/**
  * This utils are used to run the evolutions the first time the project is checked out or
  * in case the DB is whiped. This is necessary because if the DB is not initialized before
  * the slick code generation is started, the compilation task will fail due to the fact that
  * slick code generation task does not generate anything.
  */
object DatabaseUtils {
  val slickCodeGenTask = taskKey[Seq[File]]("Generates the table mapping classes to be used with slick")
  val slickOutputDir = settingKey[File]("The directory where the database mappings are outputted")
  val runEvolutions = taskKey[Seq[File]]("Runs the evolutions on the database")

  def generateSlickTables(baseDir: File, outputDir: File, classPath: Seq[File], runner: ScalaRun, stream: TaskStreams): Seq[File] = {
    val config = ConfigFactory.parseFile(new File(s"${baseDir.getAbsolutePath}/conf/application.conf")).resolve()
    val url = config.getString("slick.dbs.default.db.url")
    val jdbcDriver = config.getString("slick.dbs.default.db.driver")
    val slickProfile = config.getString("slick.dbs.default.profile").replace('$', ' ').trim
    val pkg = config.getString("slick.dbs.default.codegen.package")
    val user = config.getString("slick.dbs.default.db.user")
    val password = config.getString("slick.dbs.default.db.password")
    val ignoreInvalidDefaults = "true"
    val generatorClass = "infrastructure.JodaAwareSourceCodeGenerator"
    val outputMultipleFiles = "true"
    val generatorOptions = Array(slickProfile, jdbcDriver, url, outputDir.getPath, pkg, user, password, ignoreInvalidDefaults, generatorClass, outputMultipleFiles)
    runner.run("slick.codegen.SourceCodeGenerator", classPath, generatorOptions, stream.log).failed foreach (sys error _.getMessage)
    stream.log.info("Written slick mappings in directory " + outputDir)
    (outputDir / pkg.replace('.', '/')).listFiles()
  }

  /**
    * Copies the application.conf and the evolutions directory in the output directory.
    * This is used only when the project is checked out fresh, it is needed to run the
    * first time the evolutions. During the development this task is taken care by the
    * resourceGenerators
    */
  def copyConfigAndEvolutions(resourcesDir: File, outputDir: File): Unit = {
    if (!(outputDir / "application.conf").exists()) {
      // Copy config
      val srcConf = resourcesDir / "application.conf"
      val dstConf = outputDir / "application.conf"
      IO.copyFile(srcConf, dstConf, CopyOptions(true, true, true))

      // Copy evolutions
      val srcEvo = resourcesDir / "evolutions/default"
      val dstEvo = outputDir / "evolutions/default"
      IO.copyDirectory(srcEvo, dstEvo, CopyOptions(true, true, true))
    }
  }


  val settings = Seq(

    runEvolutions := {
      // Compiling necessary class to run evolutions
      val srcDir = (Compile / sourceDirectory).value
      val compilers = Keys.compilers.value
      val classPath = (Compile / dependencyClasspath).value
      val filesToCompile = Seq(
        srcDir / "infrastructure/ApplyEvolutions.scala",
        srcDir / "infrastructure/JodaAwareSourceCodeGenerator.scala"
      )
      val outputDir = (Compile / classDirectory).value
      val resourcesDir = (Compile / resourceDirectory).value
      val options = Shared.compileOptions
      val maxErrors = 1000
      val stream = streams.value

      copyConfigAndEvolutions(resourcesDir, outputDir)

      QuickCompiler.scalac(
        compilers,
        filesToCompile,
        QuickCompiler.noChanges,
        classPath.map(_.data),
        outputDir,
        options,
        QuickCompiler.noopCallback,
        maxErrors,
        stream.log
      )

      // Running evolutions
      val baseDir = baseDirectory.value
      val runner = (Compile / Keys.runner).value

      runner.run("infrastructure.ApplyEvolutions", classPath.files.+:(outputDir), Array(baseDir.getAbsolutePath), stream.log).failed foreach (sys error _.getMessage)
      Seq()
    },

    slickOutputDir := {
      val baseDir = baseDirectory.value
      val sourceManagedDir = sourceManaged.value
      val config = ConfigFactory.parseFile(new File(s"${baseDir.getAbsolutePath}/conf/application.conf")).resolve()

      sourceManagedDir / config.getString("slick.dbs.default.codegen.outputDir")
    },

    slickCodeGenTask := {
      runEvolutions.value
      val dir = slickOutputDir.value
      val baseDir = baseDirectory.value
      val classPath = (Compile / dependencyClasspath).value.files :+ (Compile / classDirectory).value
      val runner = (Compile / Keys.runner).value
      val stream = streams.value
      generateSlickTables(baseDir, dir, classPath, runner, stream)
    },

    Compile / managedSourceDirectories += slickOutputDir.value,
    Compile / sourceGenerators += slickCodeGenTask,

  )
}
