package something

trait RequestContext {

  def productRepo: ProductRepoLike

  def pictureRepo: PictureRepoLike
}
