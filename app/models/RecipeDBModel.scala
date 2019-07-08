package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import database.{Recipe => DBRecipe}
import utils.database.tables.RecipeTable

import scala.concurrent.{ExecutionContext, Future}

final class RecipeDBModel @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  def listOfRecipes: Future[Seq[DBRecipe]] = db.run(RecipeTable.query.result)


}
