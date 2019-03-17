package reactrouter

import slinky.core.ExternalComponent

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport

@JSImport("react-router-dom", JSImport.Default)
@js.native
object ReactNavLink extends js.Object {
  val NavLink: js.Object = js.native
}

object NavLink extends ExternalComponent {
  case class Props(activeClassName: UndefOr[String] = js.undefined,
                   activeStyle: UndefOr[js.Object] = js.undefined,
                   exact: Boolean = false,
                   strict: Boolean = false,
                   isActive: UndefOr[js.Function2[js.Object, js.Object, Boolean]] = js.undefined,
                   location: UndefOr[js.Object] = js.undefined,
                   `aria-current`: String = "page")

  override val component = ReactNavLink.NavLink
}
