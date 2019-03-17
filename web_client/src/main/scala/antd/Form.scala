package antd

import antd.FormTypes.Value
import org.scalajs.dom.Event
import slinky.core.ExternalComponent
import slinky.core.ReactComponentClass
import slinky.core.annotations.react
import slinky.core.facade.ReactElement

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
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

@js.native
trait FormOptions extends js.Object {
  val name: UndefOr[String] = js.native
  val validateMessages: js.Object = js.native
}

object FormOptions {
  def apply(name: UndefOr[String] = js.undefined,
            validateMessages: js.Object = js.Object()) =
    js.Dynamic.literal(
      name = name,
      validateMessages = validateMessages
    ).asInstanceOf[FormOptions]
}

@js.native
trait FieldProperties extends js.Object {
  val value: String = js.native
  val errors: js.Array[js.Error] = js.native
}

object FieldProperties {
  def apply(value: String, errors: Seq[js.Error]) =
    js.Dynamic.literal(value = value, errors = errors.toJSArray).asInstanceOf[FieldProperties]
}

@js.native
trait ValidationOptions extends js.Object {
  val first: Boolean = js.native
  val firstFields: js.Array[String] = js.native
  val force: Boolean = js.native
  val scroll: js.Object = js.native
}

object ValidationOptions {
  def apply(first: Boolean = true,
            firstFields: Seq[String] = Seq(),
            force: Boolean = false,
            scroll: js.Object = js.Object()) =
    js.Dynamic.literal(
      first = first,
      firstFields = firstFields.toJSArray,
      force = force,
      scroll = scroll
    ).asInstanceOf[ValidationOptions]

  def apply() = js.Object().asInstanceOf[ValidationOptions]
}

@js.native
trait ValidationError extends js.Object {
  val message: String = js.native
  val field: String = js.native
}

@js.native
trait ValidationErrorList extends js.Object {
  val errors: js.Array[ValidationError]
}

@js.native
trait ValidationRules extends js.Object {
  val enum: UndefOr[String] = js.native
  val len: UndefOr[Int] = js.native
  val max: UndefOr[Int] = js.native
  val message: UndefOr[String | ReactElement] = js.native
  val min: UndefOr[Int] = js.native
  val pattern: UndefOr[RegExp] = js.native
  val required: Boolean = js.native
  val transform: UndefOr[Value => js.Any] = js.native
  val `type`: String = js.native
  //validator -> TODO = js.native
  val whitespace: Boolean = js.native
}

object ValidationRules {

  def apply(enum: UndefOr[String] = js.undefined,
            len: UndefOr[Int] = js.undefined,
            max: UndefOr[Int] = js.undefined,
            message: UndefOr[String | ReactElement] = js.undefined,
            min: UndefOr[Int] = js.undefined,
            pattern: UndefOr[RegExp] = js.undefined,
            required: Boolean = false,
            transform: UndefOr[Value => js.Any] = js.undefined,
            `type`: String = "string",
            whitespace: Boolean = false) =
    js.Dynamic.literal(
      "enum" -> enum,
      "len" -> len,
      "max" -> max,
      "message" -> message.asInstanceOf[js.Any],
      "min" -> min,
      "pattern" -> pattern,
      "required" -> required,
      "transform" -> transform,
      "type" -> `type`,
      "whitespace" -> whitespace
    ).asInstanceOf[ValidationRules]
}

@js.native
trait FieldDecoratorOptions extends js.Object {
  val getValueFromEvent: UndefOr[Event => js.Object] = js.native
  val getValueProps: UndefOr[Value => js.Object] = js.native
  val initialValue: UndefOr[js.Object] = js.native
  val normalize: UndefOr[(Value, Value, Value) => js.Object] = js.native
  val preserve: UndefOr[Boolean] = js.native
  val rules: UndefOr[js.Array[ValidationRules]] = js.native
  val validateFirst: Boolean = js.native
  val trigger: String = js.native
  val validateTrigger: String | js.Array[String] = js.native
  val valuePropName: String = js.native
}

object FieldDecoratorOptions {
  def apply(getValueFromEvent: UndefOr[Event => js.Object] = js.undefined,
            getValueProps: UndefOr[Value => js.Object] = js.undefined,
            initialValue: UndefOr[js.Object] = js.undefined,
            normalize: UndefOr[(Value, Value, Value) => js.Object] = js.undefined,
            preserve: UndefOr[Boolean] = js.undefined,
            rules: UndefOr[Seq[ValidationRules]] = js.undefined,
            validateFirst: Boolean = false,
            trigger: String = "onChange",
            validateTrigger: String | Seq[String] = "onChange",
            valuePropName: String = "value") =
    js.Dynamic.literal(
      getValueFromEvent = getValueFromEvent,
      getValueProps = getValueProps,
      initialValue = initialValue,
      normalize = normalize,
      preserve = preserve,
      rules = rules.map(_.toJSArray),
      validateFirst = validateFirst,
      trigger = trigger,
      validateTrigger = validateTrigger.asInstanceOf[js.Any],
      valuePropName = valuePropName
    ).asInstanceOf[FieldDecoratorOptions]
}

@react object Form extends ExternalComponent {

  case class Props(hideRequiredMark: Boolean = false,
                   layout: String = "horizontal",
                   onSubmit: UndefOr[Event => Unit] = js.undefined)

  override val component = AntForm.Form

}

@js.native
trait FormOps extends js.Object {

  def getFieldDecorator(id: String, options: FieldDecoratorOptions): js.Function1[ReactElement, ReactElement] = js.native

  def getFieldError(name: String): String = js.native

  def getFieldsError(names: js.Array[String]): js.Dictionary[js.Array[String]] = js.native

  def getFieldsError(): js.Dictionary[js.Array[String]] = js.native

  def getFieldsValue(names: js.Array[String]): js.Dictionary[Value] = js.native

  def getFieldsValue(): js.Dictionary[Value] = js.native

  def isFieldsTouched(names: js.Array[String]): Boolean = js.native

  def isFieldTouched(name: String): Boolean = js.native

  def isFieldValidating(name: String): Boolean = js.native

  def resetFields(names: js.Array[String]): Unit = js.native

  def resetFields(): Unit = js.native

  def setFields(fields: js.Dictionary[FieldProperties]): Unit = js.native

  def setFieldsValue(fields: js.Dictionary[String]): Unit = js.native

  def validateFields(fieldNames: js.Dictionary[String],
                     options: ValidationOptions,
                     callback: js.Function2[js.Dictionary[ValidationErrorList], js.Dictionary[Value], Unit]): Unit = js.native
  def validateFields(callback: js.Function2[js.Dictionary[ValidationErrorList], js.Dictionary[Value], Unit]): Unit = js.native

  def validateFieldsAndScroll(fieldNames: js.Dictionary[String],
                              options: ValidationOptions,
                              callback: js.Function2[js.Dictionary[ValidationErrorList], js.Dictionary[Value], Unit]): Unit = js.native
  def validateFieldsAndScroll(callback: js.Function2[js.Dictionary[ValidationErrorList], js.Dictionary[Value], Unit]): Unit = js.native

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

