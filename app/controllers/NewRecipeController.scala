package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc._
import utils.constants.Constants

import scala.concurrent.ExecutionContext

final class NewRecipeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                                         (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { Ok(views.html.main(Constants.`new recipe`)) }

}
