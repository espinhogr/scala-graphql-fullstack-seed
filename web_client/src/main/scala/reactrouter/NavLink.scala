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
object ReactNavLink extends js.Object {
  val NavLink: js.Object = js.native
}

object NavLink extends ExternalComponentWithAttributes[a.tag.type] {
  case class Props(activeClassName: UndefOr[String] = js.undefined,
                   activeStyle: UndefOr[js.Object] = js.undefined,
                   exact: Boolean = false,
                   strict: Boolean = false,
                   isActive: UndefOr[js.Function2[js.Object, js.Object, Boolean]] = js.undefined,
                   location: UndefOr[js.Object] = js.undefined,
                   `aria-current`: String = "page",
                   to: String | js.Object,
                   replace: Boolean = false)

  override val component = ReactNavLink.NavLink
}
