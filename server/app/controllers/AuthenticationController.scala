package controllers

import auth.AuthEnvironment
import auth.UserIdentityService
import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.Inject
import play.Logger
import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.Future
import scala.util.Try

class AuthenticationController @Inject()(cc: ControllerComponents,
                                         env: Environment[AuthEnvironment],
                                         credentialsProvider: CredentialsProvider,
                                         userIdentityService: UserIdentityService) extends AbstractController(cc) {

  def authenticate = Action.async(parse.json) { implicit request =>
    request.body.validate[AuthCredentials].fold(
      errors => Future.successful(BadRequest(errors.mkString(","))),
      reqCredentials => {
        val credentials = Credentials(reqCredentials.username, reqCredentials.password)
        val bearerAuthenticatorToken = for {
          loginInfo <- credentialsProvider.authenticate(credentials)
          maybeUser <- userIdentityService.retrieve(loginInfo)
          authenticator <- maybeUser match {
            case None => Future.failed(new IdentityNotFoundException("Couldn't authenticate the user"))
            case Some(user) => env.authenticatorService.create(loginInfo)
          }
          token <- env.authenticatorService.init(authenticator)
        } yield token

        bearerAuthenticatorToken.map { token =>
          Ok(s"$token")
        } recover {
          case t => Unauthorized("Unable to login")
        }
      }
    )
  }

}

case class AuthCredentials(username: String,
                           password: String)
object AuthCredentials {
  implicit val fmt: Reads[AuthCredentials] = Json.reads[AuthCredentials]
}
