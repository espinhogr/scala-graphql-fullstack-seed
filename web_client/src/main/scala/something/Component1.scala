package something

import antd.AntForm
import antd.Button
import antd.Card
import antd.CardMeta
import antd.Col
import antd.Form
import antd.Input
import antd.Layout
import antd.LayoutContent
import antd.LayoutFooter
import antd.LayoutHeader
import antd.Row
import com.apollographql.scalajs.react.Mutation
import com.apollographql.scalajs.react.Query
import com.apollographql.scalajs.react.QueryData
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLInputElement
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.React
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.UndefOr._
import scala.scalajs.js.annotation.JSImport
import slinky.core.ReactComponentClass._

import scala.scalajs.js.JSON

@JSImport("resources/Component1.css", JSImport.Default)
@js.native
object Component1CSS extends js.Object

private[something] case class Product(name: String, description: String, image: String)

@react class Component1 extends Component {
  type Props = Unit
  case class State(allProducts: Seq[Product],
                   newProduct: Product)

  private val css = Component1CSS

  override def initialState: State = State(Seq(), Product("","",""))

  def render() = {
    Query(MyProductQuery, MyProductQuery.Variables("2", 500)) { result =>
      if (result.loading) {
        h1("Loading!")
      } else if (result.error.isDefined) {
        h1("Error: " + result.error.get.message)
      } else {
        Layout(Layout.Props())(
          renderHeader(),
          renderContent(result),
          LayoutFooter(LayoutFooter.Props())(
            span("This is a footer")
          )
        )
      }
    }
  }

  private def renderContent(result: QueryData[MyProductQuery.Data]) = {
    val wrappedProductForm = AntForm.Form.create()(wrapperToClass(ProductForm))

    LayoutContent(LayoutContent.Props())(style := js.Dynamic.literal(padding = "50px"))(
      Row(Row.Props(gutter = 16, justify = "space-around", align = "middle"))(
        Col(Col.Props(span = 6))(
          Card(Card.Props(
            cover = img(src := result.data.get.product.get.pictures.headOption.flatMap(_.url).getOrElse("default_item.jpg"))()
          ))(style := js.Dynamic.literal(width = "240px"))(
            CardMeta(
              CardMeta.Props(
                title = span(result.data.get.product.get.name))
            )
          )
        )
      ),
      hr(style := js.Dynamic.literal(margin = "30px")),
      Row(Row.Props())(
        Col(Col.Props(span = 12))(
          Input(Input.Props(
            value = state.newProduct.name,
            onChange = (e: Event) => setState(state.copy(newProduct = Product(e.target.asInstanceOf[HTMLInputElement].value, "", "")))
          ))(placeholder := "Insert the name here"),
          Mutation(AddProductMutation) { (addProduct, mutationStatus) =>
            Button(Button.Props(onClick = (e: Event) => {
              addProduct(AddProductMutation.Variables(state.newProduct.name, "NewDescription"))
              ()
            }))("Submit")
          }
        ),
        Col(Col.Props(span = 12))(
          h1("Something"),
          React.createElement(wrappedProductForm, js.Dictionary())
        )
      )
    )
  }

  private def renderHeader() = {
    LayoutHeader(LayoutHeader.Props())(
      h1(className := "App-title")("Welcome to React (with Scala.js!) on Play")
    )
  }
}

