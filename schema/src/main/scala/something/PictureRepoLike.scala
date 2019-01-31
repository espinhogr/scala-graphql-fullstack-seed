package something

import something.Domain.Picture

trait PictureRepoLike {

  def picturesByProduct(id: String): List[Picture]

}
