package something

import something.Domain.{Product => RetailProduct}

trait ProductRepoLike {

  def product(id: String): Option[RetailProduct]

  def products: List[RetailProduct]

  def addProduct(name: String, description: String): RetailProduct

}
