package antd

import org.scalajs.dom.Event
import slinky.core.ExternalComponent
import slinky.core.annotations.react

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("antd", JSImport.Default)
@js.native
object AntButton extends js.Object {
  val Button: js.Object = js.native
}

@js.native
trait Delay extends js.Object {
  val delay: Int = js.native
}
object Delay {
  def apply(delay: Int) = js.Dynamic.literal(delay = delay).asInstanceOf[Delay]
}


@react object Button extends ExternalComponent {
  case class Props(disabled: Boolean = false,
                   ghost: Boolean = false,
                   href: UndefOr[String] = js.undefined,
                   htmlType: String = "button",
                   icon: UndefOr[String] = js.undefined,
                   loading: Boolean | Delay = false,
                   shape: UndefOr[String] = js.undefined,
                   size: String = "default",
                   target: UndefOr[String] = js.undefined,
                   `type`: String = "default",
                   onClick: UndefOr[Event => Unit] = js.undefined,
                   block: Boolean = false)

  override val component = AntButton.Button
}



