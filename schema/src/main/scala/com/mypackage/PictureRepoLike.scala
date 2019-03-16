package com.mypackage

import com.mypackage.Domain.Picture

trait PictureRepoLike {

  def picturesByProduct(id: String): List[Picture]

}
