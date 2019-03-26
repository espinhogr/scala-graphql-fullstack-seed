package auth

import com.github.tototoshi.slick.MySQLJodaSupport._
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator
import database.Tables
import javax.inject.Inject
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.implicitConversions

class UserSessionRepository @Inject()(db: Database) extends AuthenticatorRepository[BearerTokenAuthenticator] {

  override def find(id: String): Future[Option[BearerTokenAuthenticator]] = {
    val query = Tables.Usersession.filter(_.id === id).result.headOption
    db.run(query).map(_.map { a =>
      val loginInfo = LoginInfo(a.logininfoid, a.logininfokey)
      BearerTokenAuthenticator(a.id, loginInfo, a.lastused, a.expiration, a.idletimeout.map(secondsToDuration))
    })
  }

  override def add(a: BearerTokenAuthenticator): Future[BearerTokenAuthenticator] = {
    val query = Tables.Usersession.map(us => (us.id, us.logininfoid, us.logininfokey, us.lastused, us.expiration, us.idletimeout)) += (
      (a.id, a.loginInfo.providerID, a.loginInfo.providerKey,
        a.lastUsedDateTime, a.expirationDateTime, a.idleTimeout.map(durationToSeconds))
    )
    db.run(query).map(_ => a)
  }

  override def update(a: BearerTokenAuthenticator): Future[BearerTokenAuthenticator] = {
    val query = Tables.Usersession.filter(_.id === a.id)
      .map(us => (us.logininfoid, us.logininfokey, us.lastused, us.expiration, us.idletimeout))
      .update((a.loginInfo.providerID, a.loginInfo.providerKey,
        a.lastUsedDateTime, a.expirationDateTime, a.idleTimeout.map(durationToSeconds)))
    db.run(query).map(_ => a)
  }

  override def remove(id: String): Future[Unit] = {
    val query = Tables.Usersession.filter(_.id === id).delete
    db.run(query).map(_ => ())
  }

  private def secondsToDuration(s: Int): FiniteDuration = s seconds
  private def durationToSeconds(f: FiniteDuration): Int = f.toSeconds.toInt

}
