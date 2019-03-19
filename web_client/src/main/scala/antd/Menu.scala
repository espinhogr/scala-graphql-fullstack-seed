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
object AntMenu extends js.Object {
  val Menu: AntMenuObject = js.native
}

@js.native
trait AntMenuObject extends js.Object {
  val Item: js.Object = js.native
  val SubMenu: js.Object = js.native
  val ItemGroup: js.Object = js.native
  val Divider: js.Object = js.native
}

@react object Menu extends ExternalComponent {
  case class Props(defaultOpenKeys: UndefOr[js.Array[String]] = js.undefined,
                   defaultSelectedKeys: UndefOr[js.Array[String]] = js.undefined,
                   forceSubMenuRender: Boolean = false,
                   inlineCollapsed: UndefOr[Boolean] = js.undefined,
                   inlineIndent: Int = 24,
                   mode: String = "vertical",
                   multiple: Boolean = false,
                   openKeys: UndefOr[js.Array[String]] = js.undefined,
                   selectable: Boolean = true,
                   selectedKeys: UndefOr[js.Array[String]] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined,
                   subMenuCloseDelay: Double = 0.1,
                   subMenuOpenDelay: Double = 0.0,
                   theme: String = "light",
                   onClick: UndefOr[js.Function3[js.Object, js.Object, js.Object, js.Any]] = js.undefined,
                   onDeselect: UndefOr[js.Function3[js.Object, js.Object, js.Object, js.Any]] = js.undefined,
                   onOpenChange: UndefOr[js.Function1[js.Array[String], js.Any]] = js.undefined,
                   onSelect: UndefOr[js.Function3[js.Object, js.Object, js.Object, js.Any]] = js.undefined)

  override val component = AntMenu.Menu
}

@react object MenuItem extends ExternalComponent {
  case class Props(disabled: Boolean = false,
                   key: UndefOr[String] = js.undefined,
                   title: UndefOr[String] = js.undefined)

  override val component = AntMenu.Menu.Item
}

@react object SubMenu extends ExternalComponent {
  case class Props(disabled: Boolean = false,
                   key: UndefOr[String] = js.undefined,
                   title: UndefOr[String] = js.undefined,
                   onTitleClick: UndefOr[js.Function2[js.Object, js.Object, js.Any]] = js.undefined)

  override val component = AntMenu.Menu.SubMenu
}

@react object ItemGroup extends ExternalComponent {
  case class Props(title: UndefOr[String | ReactElement] = js.undefined)

  override val component = AntMenu.Menu.ItemGroup
}

@react object Divider extends ExternalComponent {
  type Props = Unit

  override val component = AntMenu.Menu.Divider
}