package controllers

import database.Recipe
import javax.inject._
import models.RecipeDBModel
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import play.api.mvc._
import slick.jdbc.JdbcProfile
import utils.constants.Constants
import utils.json.UpickleJson

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
final class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                     recipeDBModel: RecipeDBModel, cc: ControllerComponents)
                                    (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.basiclaminar(Constants.`welcome to emplish list`))
  }

  implicit val recipeWrites: Writes[Seq[Recipe]] = UpickleJson.writes[Seq[Recipe]]

  def listOfRecipes: Action[AnyContent] = Action.async {
    recipeDBModel.listOfRecipes.map(Json.toJson(_)).map(Ok(_))
  }


}