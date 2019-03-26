package reactrouter

import slinky.core.ExternalComponent
import slinky.core.ExternalComponentWithAttributes
import slinky.web.html.a

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("react-router-dom", JSImport.Default)
@js.native
object ReactRedirect extends js.Object {
  val Redirect: js.Object = js.native
}

object Redirect extends ExternalComponent {
  case class Props(push: Boolean = false,
                   exact: Boolean = false,
                   strict: Boolean = false,
                   to: String | js.Object)

  override val component = ReactRedirect.Redirect
}
