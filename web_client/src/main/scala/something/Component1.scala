package something

import antd.Button
import antd.Input
import com.apollographql.scalajs.react.Mutation
import com.apollographql.scalajs.react.Query
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLInputElement
import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._

import scala.scalajs.js
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
          Input(Input.Props(
            value = state.productName,
            onChange = (e: Event) => setState(state.copy(productName = e.target.asInstanceOf[HTMLInputElement].value))
          ))(placeholder := "Insert the name here"),
          Mutation(AddProductMutation) { (addProduct, mutationStatus) => {
            Button(Button.Props(onClick = (e: Event) => {
              addProduct(AddProductMutation.Variables(state.productName, "NewDescription"))
              ()
            }))("Submit")
          }
          }
        )
      }

    }
  }
}

