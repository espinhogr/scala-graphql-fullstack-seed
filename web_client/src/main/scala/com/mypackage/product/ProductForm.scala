package com.mypackage.product

import antd.Button
import antd.FieldDecoratorOptions
import antd.Form
import antd.FormItem
import antd.FormOps
import antd.Input
import antd.StaticProps
import antd.ValidationRules
import com.apollographql.scalajs.GraphQLMutation
import com.apollographql.scalajs.react.CallMutationProps
import com.apollographql.scalajs.react.Mutation
import com.apollographql.scalajs.react.MutationResult
import com.apollographql.scalajs.react.UpdateStrategy
import com.mypackage.AddProductMutation
import com.mypackage.product.MutationType.MutationCallback
import org.scalajs.dom.Event
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.autoComplete
import slinky.web.html.placeholder

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.scalajs.js.RegExp

object MutationType {
  type MutationCallback[Q <: GraphQLMutation] = CallMutationProps[Q#Variables] => Future[MutationResult[Q#Data]]
}

@react class ProductForm extends StatelessComponent {
  type Props = Unit

  def handleSubmit(e: Event,
                   submitCall: MutationCallback[AddProductMutation.type]): Unit = {
    e.preventDefault()
    form.validateFields((errors, values) => {
      if (errors != null) {
        println("Received errors " + JSON.stringify(errors))
      } else {
        println("Received values " + JSON.stringify(values))
        val valuesMap: mutable.Map[String, String] = values
        submitCall(AddProductMutation.Variables(valuesMap("productName"), valuesMap("productDescription"))).onComplete { _ =>
          form.setFieldsValue(Map("productName" -> "", "productDescription" -> "").toJSDictionary)
        }
      }
    })
  }

  override def render(): ReactElement =
    Mutation(AddProductMutation, UpdateStrategy(refetchQueries = Seq("AllProducts"))) { (addProduct, mutationStatus) =>
      Form(Form.Props(onSubmit = (e: Event) => {
        handleSubmit(e, addProduct)
      }))(
        FormItem(
          form.getFieldDecorator("productName", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Cannot contain numbers or be empty.", pattern = RegExp("^[^0-9]+$")))))(
            Input(Input.Props())(placeholder := "Name", autoComplete := "off")
          )
        ),
        FormItem(
          form.getFieldDecorator("productDescription", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Cannot be empty."))))(
            Input(Input.Props())(placeholder := "Description", autoComplete := "off")
          )
        ),
        Button(Button.Props(`type` = "primary", htmlType = "submit"))("Add")
      )
    }

  /**
    * This is needed as Antd forms are created as higher order component
    * and a form property is injected in the props by the framework.
    * This is a workaround to access that property.
    */
  def form: FormOps = this.asInstanceOf[StaticProps].props.form
}
