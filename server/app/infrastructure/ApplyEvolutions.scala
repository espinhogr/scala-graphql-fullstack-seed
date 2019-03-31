package infrastructure

import java.io.File

import com.typesafe.config.ConfigFactory
import play.api.Configuration
import play.api.Environment
import play.api.Mode
import play.api.db.evolutions.OfflineEvolutions
import play.api.db.slick.DefaultSlickApi
import play.api.db.slick.evolutions.SlickDBApi
import play.api.inject.DefaultApplicationLifecycle
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This class applies Evolutions without the need to start the play application.
  *
  * It is used because the first time you check-out the code and you compile it, the
  * database is empty and therefore no slick code generation can be done. An empty
  * database leads to a empty code generation and therefore compilation errors.
  *
  * This is run before the code generation so that the database is initialized with
  * the evolutions and the code generation can take place.
  */
object ApplyEvolutions {

  def main(argv: Array[String]) = {
    val baseDir = argv(0)
    val config = ConfigFactory.load("application.conf")
    val environment = Environment(new File(baseDir), this.getClass.getClassLoader, Mode.Dev)

    val dbApi = SlickDBApi(new DefaultSlickApi(
      environment,
      Configuration(config),
      new DefaultApplicationLifecycle
    ))

    OfflineEvolutions.applyScript(
      new File("."),
      this.getClass.getClassLoader,
      dbApi,
      dbName = "default"
    )
  }
}
