package controllers

import javax.inject.Inject
import models.exceptions.RecipeDoesNotExist
import models.RecipeDBModel
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.filters.csrf.CSRF

import scala.concurrent.ExecutionContext

final class RecipeController @Inject()(recipeDBModel: RecipeDBModel, cc: ControllerComponents)
                                      (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def displayRecipe(recipeName: String): Action[AnyContent] = Action.async { implicit request =>
    recipeDBModel.recipe(recipeName)
      .map(views.html.viewrecipe(_, CSRF.getToken.get))
      .map(Ok(_))
      .recover {
        case t: RecipeDoesNotExist => Redirect(routes.HomeController.index()).flashing("recipe" -> t.getMessage)
      }
  }

  def deleteRecipe(recipeId: Int): Action[AnyContent] = Action.async {
    recipeDBModel.deleteRecipe(recipeId)
      .map(_ => Ok("deleted"))
  }

}
