package com.mypackage

import antd.AntForm
import antd.Col
import antd.Layout
import antd.LayoutContent
import antd.LayoutFooter
import antd.LayoutHeader
import antd.Row
import slinky.core.ReactComponentClass._
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.React
import slinky.web.html._
import util.Version

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/MainPage.css", JSImport.Default)
@js.native
object MainPageCSS extends js.Object

private[mypackage] case class Product(name: String, description: String, image: String)

@react class MainPage extends Component {
  type Props = Unit
  case class State(allProducts: Seq[Product])

  private val css = MainPageCSS

  override def initialState: State = State(Seq())

  def render() = {
    Layout(Layout.Props())(
      renderHeader(),
      renderContent(),
      LayoutFooter(LayoutFooter.Props())(
        span(s"Version ${Version.version}")
      )
    )
  }

  private def renderContent() = {
    val wrappedProductForm = AntForm.Form.create()(wrapperToClass(ProductForm))

    LayoutContent(LayoutContent.Props())(style := js.Dynamic.literal(padding = "50px"))(
      ProductDisplay(),
      hr(style := js.Dynamic.literal(margin = "30px")),
      Row(Row.Props())(
        Col(Col.Props(span = 24))(
          h1("Insert a new product"),
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

