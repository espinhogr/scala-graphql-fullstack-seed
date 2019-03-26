package com.mypackage

import antd.AntForm
import reactrouter.BrowserRouter
import reactrouter.Redirect
import reactrouter.Route
import slinky.core.Component
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.ReactComponentClass._
import slinky.core.facade.ReactElement
import slinky.web.html.h1
import authentication.Login
import org.scalajs.dom.ext.LocalStorage
import reactrouter.RouteProps
import slinky.core.facade.React
import slinky.web.svg.path

import scala.scalajs.js

@react class App extends Component {
  type Props = Unit

  case class State(isLoggingIn: Boolean, isLoggedIn: Boolean)

  override def initialState: State = State(false, false)

  val loginComponent: js.Function1[RouteProps, ReactElement] = routeProps => {
    val wrappedLoginPage = AntForm.Form.create()(wrapperToClass(Login))
    React.createElement(wrappedLoginPage, js.Dictionary("history" -> routeProps.history))
  }
  val rootPath: js.Function1[RouteProps, ReactElement] = p => Redirect(Redirect.Props(to = "/app"))
  val renderAppIfLoggedIn: js.Function1[RouteProps, ReactElement] = p =>
    if (LocalStorage("bearerToken").isEmpty)
      Redirect(Redirect.Props(to = "/login"))
    else
      MainPage()


  def render() = {

    BrowserRouter(
      Route(Route.Props(path = "/login", exact = true, render = loginComponent)),
      Route(Route.Props(path = "/app", render = renderAppIfLoggedIn)),
      Route(Route.Props(path = "/", exact = true, render = rootPath))
    )
  }
}
