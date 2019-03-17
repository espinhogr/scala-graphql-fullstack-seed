package reactrouter

import slinky.core.ExternalComponent
import slinky.core.annotations.react

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport

@JSImport("react-router-dom", JSImport.Default)
@js.native
object ReactBrowserRouter extends js.Object {
  val BrowserRouter: js.Object = js.native
}

@react object BrowserRouter extends ExternalComponent {
  case class Props(basename: String = "/",
                   getUserConfirmation: UndefOr[js.Function0[Boolean]] = js.undefined,
                   forceRefresh: Boolean = false,
                   keyLength: Int = 6)

  override val component = ReactBrowserRouter.BrowserRouter
}
