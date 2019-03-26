package auth

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator

trait AuthEnvironment extends Env {
  type A = BearerTokenAuthenticator
  type I = User
}
