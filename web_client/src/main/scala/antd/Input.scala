package antd

import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLInputElement
import slinky.core.ExternalComponent
import slinky.core.ExternalComponentWithAttributes
import slinky.core.ReactComponentClass
import slinky.core.annotations.react
import slinky.web.html.input

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("antd", JSImport.Default)
@js.native
object AntInput extends js.Object {
  val Input: js.Object = js.native
}

@react object Input extends ExternalComponentWithAttributes[input.tag.type] {
  case class Props(addonAfter: UndefOr[String | ReactComponentClass[_]] = js.undefined,
                   addonBefore: UndefOr[String | ReactComponentClass[_]] = js.undefined,
                   defaultValue: UndefOr[String] = js.undefined,
                   disabled: Boolean = false,
                   id: UndefOr[String] = js.undefined,
                   prefix: UndefOr[String | ReactComponentClass[_]] = js.undefined,
                   size: String = "default",
                   suffix: UndefOr[String | ReactComponentClass[_]] = js.undefined,
                   `type`: String = "text",
                   value: UndefOr[String] = js.undefined,
                   onChange: UndefOr[Event => Unit] = js.undefined,
                   onPressEnter: UndefOr[Event => Unit] = js.undefined,
                   allowClear: Boolean = false
                  )

  override val component = AntInput.Input
}



