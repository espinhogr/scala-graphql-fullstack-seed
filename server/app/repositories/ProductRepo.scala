package repositories

import com.mypackage.Domain.Product
import com.mypackage.ProductRepoLike
import database.Tables
import javax.inject.Inject
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProductRepo @Inject()(db: Database) extends ProductRepoLike {

  def product(id: String): Future[Option[Product]] = {
    val action = Tables.Product.filter(_.id === id.toInt ).result
    db.run(action).map(_.headOption.map(p => Product(p.id.toString, p.name, p.description)))
  }

  def products: Future[Seq[Product]] = {
    val action = Tables.Product.result
    db.run(action).map(_.map(p => Product(p.id.toString, p.name, p.description)))
  }

  def addProduct(name: String, description: String): Future[Product] = {
    val action = Tables.Product.map(p => (p.name, p.description)) += ((name, description))
    db.run(action).map(id => Product(id.toString, name, description))
  }
}

