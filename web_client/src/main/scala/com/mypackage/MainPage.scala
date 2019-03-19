package com.mypackage

import antd.Layout
import antd.LayoutFooter
import antd.LayoutHeader
import antd.LayoutSider
import antd.Menu
import antd.MenuItem
import reactrouter.BrowserRouter
import reactrouter.Route
import slinky.core.ReactComponentClass._
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import product.Products
import reactrouter.NavLink
import util.Version

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/MainPage.css", JSImport.Default)
@js.native
object MainPageCSS extends js.Object

@react class MainPage extends StatelessComponent {
  type Props = Unit

  private val css = MainPageCSS

  def render() = {
    Layout(Layout.Props(className = "main-layout"))(
      renderHeader(),
      renderContent(),
      LayoutFooter(LayoutFooter.Props())(
        span(s"Version ${Version.version}")
      )
    )
  }

  private def renderContent() = {
    val testComponent: js.Function0[ReactElement] = () => h1("Test")

    BrowserRouter(BrowserRouter.Props())(
      Layout(Layout.Props())(
        renderSider(),
        Layout(Layout.Props())(
          Route(Route.Props(path = "/", exact = true, component = wrapperToClass(Products))),
          Route(Route.Props(path = "/test", render = testComponent))
        )
      )
    )
  }

  private def renderHeader() =
    LayoutHeader(LayoutHeader.Props())(
      h1(className := "app-title")("Welcome to React (with Scala.js!) on Play")
    )


  private def renderSider() = {
    LayoutSider(
      Menu(
        MenuItem(
          NavLink(NavLink.Props(to = "/"))("Products")
        ),
        MenuItem(
          NavLink(NavLink.Props(to = "/test"))("Test")
        )
      )
    )
  }
}

