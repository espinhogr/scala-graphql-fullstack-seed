package com.mypackage

import com.apollographql.scalajs.ApolloBoostClient
import com.apollographql.scalajs.ApolloBoostClientOptions
import com.apollographql.scalajs.ApolloError
import com.apollographql.scalajs.link.Operation
import com.apollographql.scalajs.react.ApolloProvider
import com.mypackage.util.UrlUtils
import org.scalajs.dom
import org.scalajs.dom.ext.LocalStorage
import slinky.hot
import slinky.web.ReactDOM

import scala.scalajs.LinkingInfo
import scala.scalajs.js
import scala.scalajs.js.Dictionary
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.JSConverters._

@JSImport("antd/dist/antd.css", JSImport.Default)
@js.native
object AntdCSS extends js.Object

object Bootstrap {

  val css = AntdCSS

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

    val errorHandler: js.Function1[ApolloError, js.Any] = error => {
      if (error.networkError != null) {
        if (error.networkError.statusCode == 401) {
          LocalStorage.remove("bearerToken")
          UrlUtils.navigate("/login")
        } else {
          println(s"Status: ${error.networkError.statusCode} - Body: ${error.networkError.bodyText}")
        }
      }
    }

    val injectTokenInRequest: js.Function1[Operation, js.Promise[js.Any]] = operation => {
      js.Promise.resolve[js.Any](
        LocalStorage("bearerToken").map { token =>
          operation.setContext(
            Map(
              "headers" -> Map("X-Auth-Token" -> token).toJSDictionary
            ).toJSDictionary.asInstanceOf[Dictionary[js.Any]]
          )
          js.Object()
        }.getOrElse(js.Object()).asInstanceOf[js.Any]
      )
    }

    val client = new ApolloBoostClient(
      ApolloBoostClientOptions(
        uri = "/graphql",
        onError = errorHandler,
        request = injectTokenInRequest
      )
    )

    ReactDOM.render(ApolloProvider(client)(App()), container)

  }
}
