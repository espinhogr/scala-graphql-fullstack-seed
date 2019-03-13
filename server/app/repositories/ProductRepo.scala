package repositories

import something.Domain.Product
import something.ProductRepoLike

class ProductRepo extends ProductRepoLike {
  private var productList = Vector(
    Product("1", "Cheesecake", "Tasty"),
    Product("2", "Health Potion", "+50 HP"))

  def product(id: String): Option[Product] =
    productList find (_.id == id)

  def products: List[Product] = productList.toList

  def addProduct(name: String, description: String): Product = {
    val newId = productList.size + 1
    productList = productList :+ Product(newId.toString, name, description)
    Product(newId.toString, name, description)
  }
}

