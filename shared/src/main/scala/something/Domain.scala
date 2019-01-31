package something

object Domain {

  trait Identifiable {
    def id: String
  }

  case class Picture(width: Int, height: Int, url: Option[String])

  case class Product(id: String, name: String, description: String) extends Identifiable

}
