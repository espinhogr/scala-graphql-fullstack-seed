package com.mypackage

import com.mypackage.Domain.Picture

import scala.concurrent.Future

trait PictureRepoLike {

  def picturesByProduct(id: String): Future[Seq[Picture]]

}
