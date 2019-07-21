package controllers

import javax.inject.Inject
import models.{Category, Recipe, RecipeDBModel, RecipeForm, Season}
import play.api.data.Form
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.api.mvc._
import utils.constants.Constants
import utils.json.UpickleJson

import scala.concurrent.{ExecutionContext, Future}

final class NewRecipeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                          recipeDBModel: RecipeDBModel, cc: MessagesControllerComponents)
                                         (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val postUrl: Call = routes.NewRecipeController.postNewRecipe()

  def index(): Action[AnyContent] = Action { implicit request =>
    //Ok(views.html.basiclaminar(Constants.`new recipe`, maybeToken = CSRF.getToken))
    Ok(views.html.recipeform(
      Constants.`new recipe`, RecipeForm.form, postUrl
    )(
      Season.seasons, Category.categories
    ))
  }

  implicit val recipeWrites: Reads[Recipe] = UpickleJson.reads[Recipe]

  def postNewRecipe(): Action[AnyContent] = Action.async { implicit request =>

    val errorFunction = { formWithErrors: Form[Recipe] =>
      Future {
        BadRequest(views.html.recipeform(
          Constants.`new recipe`, RecipeForm.form, postUrl, formErrors = formWithErrors.errors
        )(
          Season.seasons, Category.categories
        ))
      }
    }

    val successFunction = { recipe: Recipe =>
      recipeDBModel.newRecipe(recipe)
          .map(_ => Redirect(routes.NewRecipeController.index()).flashing("info" -> s"Recipe added! $recipe"))

    }

    val formValidationResult = RecipeForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

//    Action(parse.json[Recipe]).async { implicit request: Request[Recipe] =>
//    recipeDBModel.newRecipe(request.body)
//      .map(_ => Ok("recipe added"))
//  }

}
