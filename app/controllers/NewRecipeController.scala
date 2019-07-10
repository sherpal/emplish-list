package controllers

import javax.inject.Inject
import models.{Recipe, RecipeDBModel}
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.api.mvc._
import play.filters.csrf.CSRF
import utils.constants.Constants
import utils.json.UpickleJson

import scala.concurrent.ExecutionContext

final class NewRecipeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                          recipeDBModel: RecipeDBModel, cc: ControllerComponents)
                                         (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(views.html.main(Constants.`new recipe`, maybeToken = CSRF.getToken))
  }

  implicit val recipeWrites: Reads[Recipe] = UpickleJson.reads[Recipe]

  def postNewRecipe(): Action[Recipe] = Action(parse.json[Recipe]).async { implicit request: Request[Recipe] =>
    recipeDBModel.newRecipe(request.body)
      .map(_ => Ok("recipe added"))
  }

}
