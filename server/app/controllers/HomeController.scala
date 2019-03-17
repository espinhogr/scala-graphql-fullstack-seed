package controllers

import javax.inject._
import play.Environment
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, env: Environment) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = dynamicPath("")

  /**
    * This is used to be able to have single page applications on the javascript side
    * so that if they dynamically change the route, it's always the index being served.
    */
  def dynamicPath(dynamic: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(env.isDev))
  }

}
