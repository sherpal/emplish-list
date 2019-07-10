package models

import javax.inject.Inject
import models.{Recipe => ModelRecipe}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import database.{Recipe => DBRecipe}
import utils.database.tables.{IngredientTable, PossibleIngredientTable, RecipeTable}

import scala.concurrent.{ExecutionContext, Future}

final class RecipeDBModel @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  def listOfRecipes: Future[Seq[DBRecipe]] = db.run(RecipeTable.query.result)

  def listOfPossibleIngredients: Future[Seq[Ingredient]] =
    db.run(PossibleIngredientTable.query.result).map(_.map(_.toModelIngredient))

  def newRecipe(recipe: ModelRecipe): Future[Unit] = {
    val (dbRecipe, dbIngredients) = recipe.toDBRecipe

    db.run(RecipeTable.query += dbRecipe)
      .map(idx => dbIngredients.map(_.copy(recipeId = idx)))
      .map(IngredientTable.query ++= _)
      .flatMap(db.run)
      .map(_ => Unit)

  }

}
