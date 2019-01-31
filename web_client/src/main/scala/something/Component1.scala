package something

import com.apollographql.scalajs.react.Mutation
import com.apollographql.scalajs.react.Query
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.TextEvent
import org.scalajs.dom.raw.HTMLInputElement
import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/Component1.css", JSImport.Default)
@js.native
object Component1CSS extends js.Object

@react class Component1 extends Component {
  type Props = Unit
  case class State(productName: String, productDescription: String)

  private val css = Component1CSS

  override def initialState: State = State("", "")

  def render() = {
    Query(MyProductQuery, MyProductQuery.Variables("2", 500)) { result =>
      if (result.loading) {
        h1("Loading!")
      } else if (result.error.isDefined) {
        h1("Error: " + result.error.get.getMessage)
      } else {
        div(className := "App")(
          header(className := "App-header")(
            h1(className := "App-title")("Welcome to React (with Scala.js!) on Play")
          ),
          p(className := "App-intro")(
            "To get started, edit ", code("App.scala"), " and save to reload. Amazing!!3"
          ),
          p("Retrieved from graphQL: " + result.data.get.product.get.name),
          p(result.data.get.product.get.pictures.map(p => img(src := p.url.get))),
          input(
            name := "nameField",
            placeholder := "Insert here the name",
            value := state.productName,
            onChange := (event => setState(state.copy(productName = event.target.asInstanceOf[HTMLInputElement].value)))
          ),
          Mutation(AddProductMutation) { (addProduct, mutationStatus) => {
            button(onClick := { () => {
              addProduct(AddProductMutation.Variables(state.productName, "NewDescription"))
            }})("Submit")
          }
          }
        )
      }

    }
  }
}

