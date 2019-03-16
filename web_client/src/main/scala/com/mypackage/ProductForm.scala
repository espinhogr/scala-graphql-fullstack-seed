package com.mypackage

import antd.Button
import antd.FieldDecoratorOptions
import antd.Form
import antd.FormItem
import antd.FormOps
import antd.Input
import antd.StaticProps
import antd.ValidationRules
import com.apollographql.scalajs.react.Mutation
import com.apollographql.scalajs.react.MutationResult
import com.apollographql.scalajs.react.UpdateStrategy
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLInputElement
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.placeholder

import scala.concurrent.Future
import scala.scalajs.js.JSON

@react class ProductForm extends Component {
  type Props = Unit
  case class State(newProduct: Product)

  override def initialState: State = State(Product("", "", ""))

  def handleSubmit(e: Event,
                   submitCall: => Future[MutationResult[AddProductMutation.Data]]): Unit = {
    e.preventDefault()
    form.validateFields(callback = (errors, values) => {
      if (errors != null) {
        println("Received errors " + JSON.stringify(errors))
      } else {
        println("Received values " + JSON.stringify(values))
        submitCall
      }
    })
  }

  override def render(): ReactElement =
    Form(Form.Props())(
      FormItem(FormItem.Props())(
        form.getFieldDecorator("product", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Please input your username"))))(
          Input(Input.Props(
            onChange = (e: Event) => setState(state.copy(newProduct = Product(e.target.asInstanceOf[HTMLInputElement].value, "Default description", "")))
          ))(placeholder := "New Product")
        )
      ),
      Mutation(AddProductMutation, UpdateStrategy(refetchQueries = Seq("AllProducts"))) { (addProduct, mutationStatus) =>
        Button(Button.Props(`type` = "primary", htmlType = "submit", onClick = (e: Event) => {
          handleSubmit(e, addProduct(AddProductMutation.Variables(state.newProduct.name, "NewDescription")))
        }))("Add")
      }
    )

  def form: FormOps = this.asInstanceOf[StaticProps].props.form
}
