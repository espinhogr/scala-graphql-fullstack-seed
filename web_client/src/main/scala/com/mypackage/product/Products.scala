package com.mypackage.product

import antd.AntForm
import antd.Col
import antd.LayoutContent
import antd.Row
import slinky.core.ReactComponentClass._
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.React
import slinky.web.html.h1
import slinky.web.html.hr
import slinky.web.html.style

import scala.scalajs.js

@react class Products extends StatelessComponent {
  type Props = Unit

  def render() = {
    val wrappedProductForm = AntForm.Form.create()(wrapperToClass(ProductForm))

    LayoutContent(LayoutContent.Props())(style := js.Dynamic.literal(padding = "50px"))(
      ProductDisplay(),
      hr(style := js.Dynamic.literal(margin = "30px")),
      Row(
        Col(Col.Props(span = 24))(
          h1("Insert a new product"),
          React.createElement(wrappedProductForm, js.Dictionary())
        )
      )
    )
  }
}
