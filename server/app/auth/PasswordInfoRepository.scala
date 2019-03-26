package auth

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import database.Tables
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PasswordInfoRepository @Inject()(db: Database) extends DelegableAuthInfoDAO[PasswordInfo] {

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    val query = loginInfoById(loginInfo).result
    db.run(query).map(_.headOption.map(pi => PasswordInfo(pi.hasher, pi.password, pi.salt)))
  }


  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    runAndReturn(addQuery(loginInfo, authInfo), authInfo)

  private def addQuery = (loginInfo: LoginInfo, authInfo: PasswordInfo) =>
  Tables.Logininfo.map(li =>
    (li.id, li.hasher, li.password, li.salt)
  ) += ((idFromLoginInfo(loginInfo), authInfo.hasher, authInfo.password, authInfo.salt))


  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    runAndReturn(updateQuery(loginInfo, authInfo), authInfo)

  private def updateQuery = (loginInfo: LoginInfo, authInfo: PasswordInfo) =>
    loginInfoById(loginInfo)
      .map(li => (li.hasher, li.password, li.salt))
      .update((authInfo.hasher, authInfo.password, authInfo.salt))

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    val query = for {
      isLoginInfoPresent <- loginInfoById(loginInfo).exists.result
      queryToUse = if (isLoginInfoPresent) updateQuery else addQuery
      action <- queryToUse(loginInfo, authInfo)
    } yield action

    runAndReturn(query, authInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = runAndReturn(loginInfoById(loginInfo).delete, ())

  private def idFromLoginInfo(loginInfo: LoginInfo) = loginInfo.providerID + ":" + loginInfo.providerKey
  private def loginInfoById(loginInfo: LoginInfo) = Tables.Logininfo.filter(_.id === idFromLoginInfo(loginInfo))
  private def runAndReturn[T](action: DBIOAction[Int, NoStream, Nothing], ret: T) = db.run(action).map(_ => ret)
}
