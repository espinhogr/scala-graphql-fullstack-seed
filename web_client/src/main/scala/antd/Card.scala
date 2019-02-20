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
object AntCard extends js.Object {
  val Card: AntCardObj = js.native
}

@js.native
trait AntCardObj extends js.Object {
  val Meta: js.Object = js.native
  val Grid: js.Object = js.native
}

@react object Card extends ExternalComponent {
  case class TabPaneHead(key: String, tab: ReactElement)

  case class Props(actions: UndefOr[Seq[ReactElement]] = js.undefined,
                   activeTabKey: UndefOr[String] = js.undefined,
                   headStyle: UndefOr[js.Object] = js.undefined,
                   bodyStyle: UndefOr[js.Object] = js.undefined,
                   bordered: Boolean = true,
                   cover: UndefOr[ReactElement] = js.undefined,
                   defaultActiveTabKey: UndefOr[String] = js.undefined,
                   extra: UndefOr[String | ReactElement] = js.undefined,
                   hoverable: Boolean = false,
                   loading: Boolean = false,
                   tabList: UndefOr[Seq[TabPaneHead]] = js.undefined,
                   size: String = "default",
                   title: UndefOr[String | ReactElement] = js.undefined,
                   `type`: UndefOr[String] = js.undefined,
                   onTabChange: UndefOr[String => Unit] = js.undefined
                   // Needs to be verified if types align with JS.
                  )

  override val component = AntCard.Card
}

@react object CardMeta extends ExternalComponent {
  case class Props(avatar: UndefOr[ReactElement] = js.undefined,
                   className: UndefOr[String] = js.undefined,
                   description: UndefOr[ReactElement] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined,
                   title: UndefOr[ReactElement] = js.undefined)

  override val component = AntCard.Card.Meta
}

@react object CardGrid extends ExternalComponent {
  case class Props(className: UndefOr[String] = js.undefined,
                   style: UndefOr[js.Object] = js.undefined)

  override val component = AntCard.Card.Grid
}
