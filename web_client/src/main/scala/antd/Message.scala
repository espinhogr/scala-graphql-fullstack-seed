package antd

import slinky.core.facade.ReactElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("antd", JSImport.Default)
@js.native
object AntMessage extends js.Object {
  val message: MessageInterface = js.native
}

@js.native
trait MessageInterface extends js.Object {
  def success(content: String | ReactElement,
              duration: js.UndefOr[Double] = 1.5,
              onClose: js.UndefOr[js.Function0[js.Any]] = js.undefined): Unit = js.native
  def error(content: String | ReactElement,
              duration: js.UndefOr[Double] = 1.5,
              onClose: js.UndefOr[js.Function0[js.Any]] = js.undefined): Unit = js.native
  def info(content: String | ReactElement,
              duration: js.UndefOr[Double] = 1.5,
              onClose: js.UndefOr[js.Function0[js.Any]] = js.undefined): Unit = js.native
  def warning(content: String | ReactElement,
              duration: js.UndefOr[Double] = 1.5,
              onClose: js.UndefOr[js.Function0[js.Any]] = js.undefined): Unit = js.native
  def loading(content: String | ReactElement,
              duration: js.UndefOr[Double] = 1.5,
              onClose: js.UndefOr[js.Function0[js.Any]] = js.undefined): Unit = js.native
}

