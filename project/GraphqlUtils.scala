import sbt.Keys._
import sbt._
import sbt.internal.inc.classpath.ClasspathUtilities

/**
  * Tools to generate at build time the SDL for graphql from the defined schema in Sangria-Scala.
  * 
  * Replace with sbt-graphql. After a fast test i've noticed that when the task in sbt-graphql used 
  * to generate the schema is added to the (Compile / resourceGenerators) task list, if the project is
  * built, sbt goes into infinite loop.
  */
object GraphqlUtils {

  val generateGraphqlSdlFromScala = taskKey[Seq[File]]("Generates the SDL from the defined schema in Sangria")
  val schemaSdlFileName = settingKey[String]("The name of the file generated when the graphql SDL is rendered")

  val generateGraphqlQueries = taskKey[Seq[File]]("Generates Scala classes definitions from queries defined in graphql")
  val queryClassesPackage = settingKey[String]("The package the generated Scala classes should belong to")
  val npmUpdateTask = taskKey[File]("Task that updates npm and returns the installation directory")

  val generateTasks = Seq(
    schemaSdlFileName := "schema.graphql",

    generateGraphqlSdlFromScala := {
      val classpath = (dependencyClasspath in Compile).value
      val schemaObject = (Schema.schema / Schema.schemaObject).value
      val outputDir = (Compile / resourceManaged).value
      val outputFile = (Compile / schemaSdlFileName).value
      val sdlFile = generateSdl(classpath, schemaObject, outputDir, outputFile)
      streams.value.log.info("Written graphql SDL in file " + sdlFile.getAbsolutePath)
      Seq(sdlFile)
    },

    Compile / resourceGenerators += Compile / generateGraphqlSdlFromScala,

    queryClassesPackage := "something",

    generateGraphqlQueries := {
      import scala.sys.process._

      (Compile / generateGraphqlSdlFromScala).value

      val outputDirectory = (Compile / sourceManaged).value
      val outputFile = outputDirectory / "graphql.scala"

      outputDirectory.mkdirs()

      val apolloDirectory = npmUpdateTask.value / "node_modules" / "apollo"
      val queriesDirectory = ((Compile / sourceDirectory).value / "graphql").getAbsolutePath

      val apolloCommand = Seq(
        (apolloDirectory / "bin" / "run").getAbsolutePath,
        "codegen:generate",
        "--includes=" + queriesDirectory + "/*.graphql",
        "--localSchemaFile=" + ((Compile / resourceManaged).value / (Compile / schemaSdlFileName).value).getAbsolutePath,
        "--namespace=" + queryClassesPackage.value,
        "--target=scala",
        outputFile.getAbsolutePath
      )

      apolloCommand.!

      streams.value.log.info("Running: " + apolloCommand)

      Seq(outputFile)
    },

    Compile / sourceGenerators += Compile / generateGraphqlQueries,

    watchSources ++= ((sourceDirectory in Compile).value / "graphql" ** "*.graphql").get
  )
  
  private def generateSdl(classpath: Classpath, schemaObjectFQN: String, outputDir: File, outputFile: String): File = {
    def scalaObjectInstance(clazz: Class[_]) = {
      val schemaObjectConstructor = clazz.getDeclaredConstructors.head
      schemaObjectConstructor.setAccessible(true)
      schemaObjectConstructor.newInstance()
    }

    def schemaDefinition(classLoader: ClassLoader) = {
      val schemaObjectRef = classLoader.loadClass(schemaObjectFQN + "$")
      val schemaObjectInstance = scalaObjectInstance(schemaObjectRef)
      val schemaField = schemaObjectRef.getMethod("schema")
      schemaField.invoke(schemaObjectInstance)
    }

    def renderSchema(classLoader: ClassLoader, schema: AnyRef): String = {
      val schemaRendererObjectRef = classLoader.loadClass("sangria.renderer.SchemaRenderer" + "$")
      val schemaClass = classLoader.loadClass("sangria.schema.Schema")
      val schemaRendererMethod = schemaRendererObjectRef.getMethod("renderSchema", schemaClass)
      val schemaRendererInstance = scalaObjectInstance(schemaRendererObjectRef)
      schemaRendererMethod.invoke(schemaRendererInstance, schema).asInstanceOf[String]
    }

    val classLoader = ClasspathUtilities.toLoader(classpath.map(_.data).map(_.getAbsoluteFile))
    val schema = schemaDefinition(classLoader)
    val sdl = renderSchema(classLoader, schema)
    val sdlFile = new File(outputDir, outputFile)
    IO.write(sdlFile, sdl)

    sdlFile
  }
}
