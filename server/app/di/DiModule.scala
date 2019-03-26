package di

import auth.AuthEnvironment
import auth.PasswordInfoRepository
import auth.User
import auth.UserIdentityService
import auth.UserSessionRepository
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.api.EventBus
import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.SilhouetteProvider
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util.Clock
import com.mohiva.play.silhouette.api.util.IDGenerator
import com.mohiva.play.silhouette.api.util.PasswordHasher
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticatorService
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticatorSettings
import com.mohiva.play.silhouette.impl.util.SecureRandomIDGenerator
import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import com.mohiva.play.silhouette.persistence.repositories.DelegableAuthInfoRepository
import com.mypackage.PictureRepoLike
import com.mypackage.ProductRepoLike
import com.mypackage.RequestContext
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration
import repositories.PictureRepo
import repositories.ProductRepo
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import DiModule._

class DiModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[PictureRepoLike].to[PictureRepo]
    bind[ProductRepoLike].to[ProductRepo]
    bind[Silhouette[AuthEnvironment]].to[SilhouetteProvider[AuthEnvironment]]
    bind[IDGenerator].toInstance(new SecureRandomIDGenerator)
    bind[Clock].toInstance(Clock())
    bind[EventBus].toInstance(EventBus())
    bind[AuthenticatorRepository[BearerTokenAuthenticator]].to[UserSessionRepository].in(classOf[Singleton])
    bind[IdentityService[User]].to[UserIdentityService].in(classOf[Singleton])
    bind[DelegableAuthInfoDAO[PasswordInfo]].to[PasswordInfoRepository]
    bind[PasswordHasher].toInstance(hasher)
  }

  @Provides
  @Singleton
  def getDatabase(): Database = {
    Database.forConfig(databaseConfigPath)
  }

  @Provides
  @Singleton
  def getRequestContext(productRepository: ProductRepoLike,
                        pictureRepository: PictureRepoLike): RequestContext = {
    new RequestContext {
      override def productRepo: ProductRepoLike = productRepository
      override def pictureRepo: PictureRepoLike = pictureRepository
    }
  }

  @Provides
  @Singleton
  def getEnvironment(is: IdentityService[User],
                     as: AuthenticatorService[BearerTokenAuthenticator],
                     eventBus: EventBus): Environment[AuthEnvironment] =
    Environment[AuthEnvironment](is, as, Seq(), eventBus)


  @Provides
  @Singleton
  def getAuthenticatorService(configuration: Configuration,
                              authenticatorRepository: AuthenticatorRepository[BearerTokenAuthenticator],
                              idGenerator: IDGenerator,
                              clock: Clock): AuthenticatorService[BearerTokenAuthenticator] = {

    new BearerTokenAuthenticatorService(
      BearerTokenAuthenticatorSettings(authenticatorExpiry = 30 days),
      authenticatorRepository,
      idGenerator,
      clock)
  }

  @Provides
  @Singleton
  def getAuthInfoRepository(passwordInfoDAO: DelegableAuthInfoDAO[PasswordInfo]): AuthInfoRepository =
    new DelegableAuthInfoRepository(passwordInfoDAO)

  @Provides
  @Singleton
  def getPasswordHasherRegistry(passwordHasher: PasswordHasher) =
    PasswordHasherRegistry(passwordHasher)

}

object DiModule {
  def databaseConfigPath = "slick.dbs.default.db"
  val hasher = new BCryptPasswordHasher()
}