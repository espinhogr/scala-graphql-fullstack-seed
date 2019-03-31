package com.mypackage

import com.mypackage.Domain.{Product => RetailProduct}

import scala.concurrent.Future

trait ProductRepoLike {

  def product(id: String): Future[Option[RetailProduct]]

  def products: Future[Seq[RetailProduct]]

  def addProduct(name: String, description: String): Future[RetailProduct]

}
