package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import utils.database.tables.{IngredientTable, PossibleIngredientTable, RecipeTable}

import scala.concurrent.{ExecutionContext, Future}

final class DatabaseInitializer @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                         (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  private def tables = Seq(
    "recipe" -> RecipeTable.query,
    "ingredient" -> IngredientTable.query,
    "possible_ingredient" -> PossibleIngredientTable.query
  )

  private def queries = tables.map(_._2.schema)

  def initialize(): Future[Unit] = db.run(DBIO.seq(queries.tail.foldLeft(queries.head)(_ ++ _).create))

}
