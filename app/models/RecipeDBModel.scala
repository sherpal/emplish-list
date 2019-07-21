package models

import javax.inject.Inject
import models.{Recipe => ModelRecipe}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import database.{Recipe => DBRecipe}
import models.exceptions.RecipeDoesNotExist
import utils.database.tables.{IngredientTable, PossibleIngredientTable, RecipeTable}

import scala.concurrent.{ExecutionContext, Future}

final class RecipeDBModel @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  def listOfRecipes: Future[Seq[DBRecipe]] = db.run(RecipeTable.query.result)

  def recipe(recipeName: String): Future[ModelRecipe] = db.run(
    RecipeTable.query.filter(_.name === recipeName)
      .joinLeft(IngredientTable.query).on(_.id === _.recipeId)
      .result
  ).map { info =>
    val dbRecipe = info.head._1
    val dbIngredients = info.flatMap(_._2)
    dbRecipe.toModelRecipe(dbIngredients)
  }
    .recover {
      case _: UnsupportedOperationException => throw new RecipeDoesNotExist(recipeName)
    }

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

  def deleteRecipe(recipeId: Int): Future[Unit] = {
    db.run(IngredientTable.query.filter(_.recipeId === recipeId).delete)
      .flatMap(_ => db.run(RecipeTable.query.filter(_.id === recipeId).delete))
      .map(_ => ())
  }

}
