package controllers

import com.google.inject.Inject
import play.api.libs.json._
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.twirl.api.Html
import repositories.PictureRepo
import repositories.ProductRepo
import sangria.ast.Document
import sangria.execution.ErrorWithResolver
import sangria.execution.Executor
import sangria.execution.QueryAnalysisError
import sangria.marshalling.playJson._
import sangria.parser.QueryParser
import something.GraphQLSchema._
import something.RequestContext

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

class GraphQLController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val execution = cc.executionContext

  val requestContext = new RequestContext {
    val productRepo = new ProductRepo
    val pictureRepo = new PictureRepo
  }

  def graphql = Action.async(parse.json) { request ⇒
    def parseVariables(variables: String) =
      if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]
    val variables = (request.body \ "variables").toOption.flatMap {
      case JsString(vars) ⇒ Some(parseVariables(vars))
      case obj: JsObject ⇒ Some(obj)
      case _ ⇒ None
    }

    QueryParser.parse(query) match {
      // query parsed successfully, time to execute it!
      case Success(queryAst) ⇒
        executeGraphQLQuery(queryAst, operation, variables.getOrElse(Json.obj()))

      // can't parse GraphQLController query, return error
      case Failure(error) ⇒
        Future.successful(BadRequest(Json.obj("error" → error.getMessage)))
    }
  }

  def executeGraphQLQuery(query: Document, op: Option[String], vars: JsObject) =
    Executor.execute(schema, query, requestContext, operationName = op, variables = vars)
      .map(Ok(_))
      .recover {
        case error: QueryAnalysisError ⇒ BadRequest(error.resolveError)
        case error: ErrorWithResolver ⇒ InternalServerError(error.resolveError)
      }

  def graphiQL = Action {
    Html
    Ok(views.html.graphiql())
  }
}
