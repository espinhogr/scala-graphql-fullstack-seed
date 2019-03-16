import sbt.Keys._
import sbt._

/**
  * Project to hold the GraphQL schema.
  * This is not in shared because there are some parts used only by the server
  * and some used only by the client. This is mainly to the fact that the schema
  * is defined using Sangria DSL, then a schema is produced programmatically and
  * it's used in the client to generate the types.
  */
object Schema {

  val schemaObject = settingKey[String]("Fully qualified name of the object containing the schema.")
  
  private[this] val schemaSettings = Shared.commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.sangria-graphql" %% "sangria" % "1.4.2",
    ),

    schemaObject := "com.mypackage.GraphQLSchema",
    
  )

  lazy val schema = (project in file("schema"))
    .settings(schemaSettings: _*)
    .dependsOn(Shared.sharedJvm)
}


