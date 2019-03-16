package com.mypackage

trait RequestContext {

  def productRepo: ProductRepoLike

  def pictureRepo: PictureRepoLike
}
