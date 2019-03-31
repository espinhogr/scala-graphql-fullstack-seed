package repositories

import com.mypackage.PictureRepoLike
import com.mypackage.Domain
import com.mypackage.Domain.Picture
import database.Tables
import javax.inject.Inject
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PictureRepo @Inject()(db: Database) extends PictureRepoLike {

  override def picturesByProduct(id: String): Future[Seq[Picture]] = {
    val action = Tables.Pictures.filter(_.productid === id.toInt).result
    db.run(action).map(_.map(p => Picture(p.width, p.height, p.url)))
  }

}
