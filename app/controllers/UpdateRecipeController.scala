package controllers

import javax.inject.Inject
import models.RecipeDBModel
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}

import scala.concurrent.ExecutionContext

final class UpdateRecipeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                             recipeDBModel: RecipeDBModel, cc: MessagesControllerComponents)
                                            (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def index(recipeId: Int): Action[AnyContent] = TODO

}
