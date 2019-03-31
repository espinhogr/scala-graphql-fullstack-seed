package di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.mypackage.PictureRepoLike
import com.mypackage.ProductRepoLike
import com.mypackage.RequestContext
import net.codingwell.scalaguice.ScalaModule
import repositories.PictureRepo
import repositories.ProductRepo
import slick.jdbc.JdbcBackend.Database

class DiModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[PictureRepoLike].to[PictureRepo]
    bind[ProductRepoLike].to[ProductRepo]
  }

  @Provides
  @Singleton
  def getDatabase(): Database = {
    Database.forConfig("slick.dbs.default.db")
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

}
