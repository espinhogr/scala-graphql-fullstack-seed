package com.mypackage.authentication

import antd.AntMessage
import antd.Button
import antd.Col
import antd.FieldDecoratorOptions
import antd.Form
import antd.FormItem
import antd.FormOps
import antd.Input
import antd.Layout
import antd.LayoutContent
import antd.Row
import antd.StaticProps
import antd.ValidationRules
import com.mypackage.AddProductMutation
import com.mypackage.util.UrlUtils
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Method
import fr.hmil.roshttp.body.Implicits._
import fr.hmil.roshttp.body.JSONBody._
import org.scalajs.dom.Event
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.web.html.autoComplete
import slinky.web.html.h1
import slinky.web.html.placeholder
import slinky.web.html.style

import scala.scalajs.js.JSConverters._
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom.ext.LocalStorage
import reactrouter.History
import reactrouter.Redirect

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.Failure
import scala.util.Success

@react class Login extends StatelessComponent {
  case class Props(history: History)

  val handleSubmit = (e: Event) => {
    e.preventDefault()

    form.validateFields((errors, values) => {
      if (errors != null) {
        println("Received errors " + JSON.stringify(errors))
      } else {
        println("Received values " + JSON.stringify(values))
        val valuesMap: mutable.Map[String, String] = values

        HttpRequest().withURL(UrlUtils.getBaseUrl + "/authenticate").post(JSONObject(
          "username" -> valuesMap("username"),
          "password" -> valuesMap("password")
        )).onComplete { result =>
          form.setFieldsValue(Map("username" -> "", "password" -> "").toJSDictionary)

          result match {
            case Failure(exception) =>
              AntMessage.message.error("Unable to login, retry!")
              println(exception.getMessage)
            case Success(result) =>
              AntMessage.message.info("Login successful")
              LocalStorage.update("bearerToken", result.body)
              props.history.push("/")
          }
        }
      }
    })
  }

  def render() = {
    if (LocalStorage("bearerToken").isEmpty) {
      Layout(
        LayoutContent(
          Row(Row.Props(`type` = "flex", align = "middle", justify = "center"))(style := js.Dynamic.literal(height = "100vh"))(
            Col(Col.Props(span = 6))(
              Form(Form.Props(onSubmit = handleSubmit))(
                FormItem(
                  form.getFieldDecorator("username", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Cannot login without username."))))(
                    Input(Input.Props())(placeholder := "Username")
                  )
                ),
                FormItem(
                  form.getFieldDecorator("password", FieldDecoratorOptions(rules = Seq(ValidationRules(required = true, message = "Cannot login without password."))))(
                    Input(Input.Props(`type` = "password"))(placeholder := "Password")
                  )
                ),
                Button(Button.Props(`type` = "primary", htmlType = "submit"))("Login")
              )
            )
          )
        )
      )
    } else {
      Redirect(Redirect.Props(to = "/"))
    }
  }

  /**
    * This is needed as Antd forms are created as higher order component
    * and a form property is injected in the props by the framework.
    * This is a workaround to access that property.
    */
  def form: FormOps = this.asInstanceOf[StaticProps].props.form
}
