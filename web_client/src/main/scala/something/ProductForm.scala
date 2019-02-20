package something

import antd.FieldDecoratorOptions
import antd.Form
import antd.FormItem
import antd.Input
import antd.StaticProps
import antd.ValidationRules
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.placeholder

@react class ProductForm extends Component {
  case class Props()
  case class State()

  override def initialState: State = State()

  override def render(): ReactElement = {

    Form(Form.Props())(
      FormItem(FormItem.Props())(
        this.asInstanceOf[StaticProps].props.form.getFieldDecorator("userName", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Please input your username"))))(
          Input(Input.Props(prefix = "a"))(placeholder := "Username")
        )
      )
    )
  }
}
