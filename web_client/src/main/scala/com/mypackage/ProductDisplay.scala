package com.mypackage

import antd.Card
import antd.CardMeta
import antd.Col
import antd.Row
import com.apollographql.scalajs.react.Query
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.web.html.div
import slinky.web.html.h1
import slinky.web.html.img
import slinky.web.html.span
import slinky.web.html.src
import slinky.web.html.style

import scala.scalajs.js

@react class ProductDisplay extends StatelessComponent {
  type Props = Unit

  override def render() =
    Query(AllProductsQuery) { result =>
      if (result.loading) {
        h1("Loading!")
      } else if (result.error.isDefined) {
        h1("Error: " + result.error.get.message)
      } else {
        div(
          h1("Products display"),
          Row(Row.Props(gutter = 16, justify = "space-around", align = "middle"))(
            result.data.get.products.map { product =>
              Col(Col.Props(span = 6))(
                Card(Card.Props(
                  cover = img(src := product.pictures.headOption.flatMap(_.url).getOrElse("/assets/images/default_item.jpg"))()
                ))(style := js.Dynamic.literal(maxWidth = "240px"))(
                  CardMeta(
                    CardMeta.Props(title = span(product.name), description = span(product.description))
                  )
                )
              )
            }: _*
          )
        )
      }
    }

}
