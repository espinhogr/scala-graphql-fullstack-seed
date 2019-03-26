package auth

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService

import scala.concurrent.Future

class UserIdentityService extends IdentityService[User] {

  /**
    * This is not implemented as I'm not saving any data related to the user,
    * this is useful to save things like email, username, etc etc.
    */
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
    Future.successful(Some(User()))
  }
}
