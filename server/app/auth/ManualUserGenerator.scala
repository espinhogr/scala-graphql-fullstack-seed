package auth

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import di.DiModule._
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * This class is used in case you want to register a user manually.
  * It inserts a record in the database with the user credentials.
  */
object ManualUserGenerator extends App {

  val username = "admin"
  val password = "admin"

  val database = Database.forConfig(databaseConfigPath)
  val passwordInfoRepository = new PasswordInfoRepository(database)

  val passwordInfo = hasher.hash(password)
  val loginInfo = LoginInfo(CredentialsProvider.ID, username)
  println(
    Await.result(passwordInfoRepository.add(loginInfo, passwordInfo), 5 seconds)
  )
}
