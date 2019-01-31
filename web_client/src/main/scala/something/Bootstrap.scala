package something

import com.apollographql.scalajs.ApolloBoostClient
import com.apollographql.scalajs.react.ApolloProvider
import org.scalajs.dom
import slinky.hot
import slinky.web.ReactDOM

import scala.scalajs.LinkingInfo
import scala.scalajs.js.annotation.JSExportTopLevel

object Bootstrap {

  @JSExportTopLevel("entrypoint.main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    val client = ApolloBoostClient(
      uri = "/graphql"
    )

    ReactDOM.render(ApolloProvider(client)(Component1()), container)

  }
}
