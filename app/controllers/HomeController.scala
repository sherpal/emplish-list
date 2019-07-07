package controllers

import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import utils.database.DBProfile.api._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
final class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                                    (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    db.run(utils.database.tables.RecipeTable.query.result)
        .map(ls => Ok(views.html.index(ls)))
  }


}
