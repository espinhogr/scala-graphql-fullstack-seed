package antd

import slinky.core.ExternalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("antd", JSImport.Default)
@js.native
object AntLayout extends js.Object {
  val Layout: AntLayoutObj = js.native
}

@js.native
trait AntLayoutObj extends js.Object {
  val Header: js.Object = js.native
  val Footer: js.Object = js.native
  val Content: js.Object = js.native
  val Sider: js.Object = js.native
}

@react object Layout extends ExternalComponent {
  case class Props(className: UndefOr[String] = js.undefined,
                   hasSider: UndefOr[Boolean] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined)

  override val component = AntLayout.Layout
}

@react object LayoutHeader extends ExternalComponent {
  case class Props(className: UndefOr[String] = js.undefined,
                   hasSider: UndefOr[Boolean] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined)

  override val component = AntLayout.Layout.Header
}

@react object LayoutFooter extends ExternalComponent {
  case class Props(className: UndefOr[String] = js.undefined,
                   hasSider: UndefOr[Boolean] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined)

  override val component = AntLayout.Layout.Footer
}

@react object LayoutContent extends ExternalComponent {
  case class Props(className: UndefOr[String] = js.undefined,
                   hasSider: UndefOr[Boolean] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined)

  override val component = AntLayout.Layout.Content
}

@react object LayoutSider extends ExternalComponent {
  case class Props(breakpoint: UndefOr[String] = js.undefined,
                   className: UndefOr[String] = js.undefined,
                   collapsed: UndefOr[Boolean] = js.undefined,
                   collapsedWidth: Int = 80,
                   collapsible: Boolean = false,
                   defaultCollapsed: Boolean = false,
                   reverseArrow: Boolean = false,
                   style: UndefOr[js.Object] = js.undefined,
                   theme: String = "dark",
                   trigger: UndefOr[String | ReactElement] = js.undefined,
                   width: Int | String = 200,
                   onCollapse: UndefOr[js.Function2[js.Object, js.Object, js.Any]] = js.undefined,
                   onBreakpoint: UndefOr[js.Function1[js.Object, js.Any]] = js.undefined)

  override val component = AntLayout.Layout.Sider
}