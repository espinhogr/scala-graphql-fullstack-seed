package antd

import antd.FormTypes.Value
import org.scalajs.dom.Event
import slinky.core.ExternalComponent
import slinky.core.ReactComponentClass
import slinky.core.annotations.react
import slinky.core.facade.ReactElement

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.RegExp
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/**
  * Forms follow the implementation at https://github.com/react-component/form
  *
  * This representation does not contain all the properties available in the
  * JS implementation, only the main have been ported.
  */


@JSImport("antd", JSImport.Default)
@js.native
object AntForm extends js.Object {
  val Form: FormObj = js.native
}

@js.native
trait FormObj extends js.Object {
  val Item: js.Object = js.native

  def create(formOptions: FormOptions = FormOptions()): js.Function1[ReactComponentClass[_], js.Object] = js.native
}

@js.native
trait FieldsNames extends js.Object {
  val names: Seq[String]
}

object FormTypes {
  type Value = String
}

case class FormOptions(name: UndefOr[String] = js.undefined,
                       validateMessages: js.Object = js.Object())
case class FieldProperties(value: String, errors: Seq[js.Error])
case class ValidationOptions(first: Boolean = true,
                             firstFields: Seq[String] = Seq(),
                             force: Boolean = false,
                             scroll: js.Object = js.Object())
case class ValidationError(message: String, field: String)
case class ValidationErrorList(errors: Seq[ValidationError])
case class ValidationRules(enum: UndefOr[String] = js.undefined,
                           len: UndefOr[Int] = js.undefined,
                           max: UndefOr[Int] = js.undefined,
                           message: UndefOr[String | ReactElement] = js.undefined,
                           min: UndefOr[Int] = js.undefined,
                           pattern: UndefOr[RegExp] = js.undefined,
                           required: Boolean = false,
                           transform: UndefOr[Value => js.Any] = js.undefined,
                           `type`: String = "string",
                           //validator -> TODO
                           whitespace: Boolean = false)
case class FieldDecoratorOptions(getValueFromEvent: UndefOr[Event => js.Object] = js.undefined,
                                 getValueProps: UndefOr[Value => js.Object] = js.undefined,
                                 initialValue: UndefOr[js.Object] = js.undefined,
                                 normalize: UndefOr[(Value, Value, Value) => js.Object] = js.undefined,
                                 preserve: UndefOr[Boolean] = js.undefined,
                                 rules: UndefOr[Seq[ValidationRules]] = js.undefined,
                                 validateFirst: Boolean = false,
                                 trigger: String = "onChange",
                                 validateTrigger: String | Seq[String] = "onChange",
                                 valuePropName: String = "value")

@react object Form extends ExternalComponent {
  case class Props(hideRequiredMark: Boolean = false,
                   layout: String = "horizontal",
                   onSubmit: UndefOr[Event => Unit] = js.undefined,
                  )

  override val component = AntForm.Form

}

@js.native
trait FormOps extends js.Object {

  def getFieldDecorator(id: String, options: FieldDecoratorOptions): js.Function1[ReactElement, ReactElement] = js.native
  def getFieldError(name: String): String = js.native
  def getFieldsError(names: Seq[String]): Map[String, Seq[String]] = js.native
  def getFieldsError(): Map[String, Seq[String]]  = js.native
  def getFieldsValue(names: Seq[String]): Map[String, Value] = js.native
  def getFieldsValue(): Map[String, Value] = js.native
  def isFieldsTouched(names: Seq[String]): Boolean = js.native
  def isFieldTouched(name: String): Boolean = js.native
  def isFieldValidating(name: String): Boolean= js.native
  def resetFields(names: Seq[String]): Unit = js.native
  def resetFields(): Unit = js.native
  def setFields(fields: Map[String, FieldProperties]): Unit = js.native
  def setFieldValue(fields: Map[String, String]): Unit = js.native
  def validateFields(fieldNames: Map[String, String] = Map(),
                     options: ValidationOptions = ValidationOptions(),
                     callback: js.Function2[js.Dictionary[ValidationErrorList], js.Dictionary[Value], Unit]): Unit = js.native
  def validateFieldsAndScroll(fieldNames: Map[String, String] = Map(),
                     options: ValidationOptions = ValidationOptions(),
                     callback: (Map[String, ValidationErrorList], Map[String, Value]) => Unit): Unit = js.native

}

@js.native
trait FormStaticProps extends js.Object {
  def form: FormOps = js.native
}

@js.native
trait StaticProps extends js.Object {
  def props: FormStaticProps = js.native
}

@react object FormItem extends ExternalComponent {
  case class Props(colon: Boolean = true,
                   extra: UndefOr[String | ReactElement] = js.undefined,
                   hasFeedback: Boolean = false,
                   help: UndefOr[String | ReactElement] = js.undefined,
                   label: UndefOr[String | ReactElement] = js.undefined,
                   labelCol: UndefOr[ColProps] = js.undefined,
                   required: Boolean = false,
                   validateStatus: UndefOr[String] = js.undefined,
                   wrapperCol: UndefOr[ColProps] = js.undefined)

  override val component = AntForm.Form.Item
}

