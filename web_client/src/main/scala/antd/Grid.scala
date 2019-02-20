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
object AntGrid extends js.Object {
  val Row: js.Object = js.native
  val Col: js.Object = js.native
}

case class Gutter(xs: Int = 0,
                  sm: Int = 0,
                  md: Int = 0,
                  lg: Int = 0,
                  xl: Int = 0,
                  xxl: Int = 0)

case class ColProps(offset: Int = 0,
                   order: Int = 0,
                   pull: Int = 0,
                   push: Int = 0,
                   span: Int = 0)

@react object Row extends ExternalComponent {
  case class Props(align: String = "top",
                   gutter: Int | Gutter = 0,
                   justify: String = "start",
                   `type`: UndefOr[String] = js.undefined)

  override val component = AntGrid.Row
}

@react object Col extends ExternalComponent {
  case class Props(offset: Int = 0,
                   order: Int = 0,
                   pull: Int = 0,
                   push: Int = 0,
                   span: Int = 0,
                   xs: UndefOr[Int | Gutter] = js.undefined,
                   sm: UndefOr[Int | Gutter] = js.undefined,
                   md: UndefOr[Int | Gutter] = js.undefined,
                   lg: UndefOr[Int | Gutter] = js.undefined,
                   xl: UndefOr[Int | Gutter] = js.undefined,
                   xxl: UndefOr[Int | Gutter] = js.undefined)

  override val component = AntGrid.Col
}

