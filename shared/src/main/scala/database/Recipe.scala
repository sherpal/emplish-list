package database

import models.{Category, Season, Recipe => ModelRecipe}

final case class Recipe(id: Int, name: String, category: String, season: String) {
  def toModelRecipe(ingredients: Seq[Ingredient]): ModelRecipe = ModelRecipe(
    id, name, ingredients.map(_.toModelIngredient), Category.category(category).get, Season.season(season).get
  )
}

object Recipe {
  import upickle.default.{ReadWriter, macroRW}
  implicit final val rw: ReadWriter[Recipe] = macroRW

  def tupled: ((Int, String, String, String)) => Recipe = (apply _).tupled
}
