package utils.database.tables

import database.Ingredient
import slick.lifted.Tag
import utils.database.DBProfile.api._


final class IngredientTable(tag: Tag) extends Table[Ingredient](tag, "ingredient") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name")
  def unit = column[String]("unit")
  def recipeId = column[Int]("recipe_id")

  def * = (id, name, unit, recipeId) <> (Ingredient.tupled, Ingredient.unapply)

  def recipe = foreignKey("sup_recipe", recipeId, RecipeTable.query)(_.id)

}

object IngredientTable {

  def query: TableQuery[IngredientTable] = TableQuery[IngredientTable]

}
