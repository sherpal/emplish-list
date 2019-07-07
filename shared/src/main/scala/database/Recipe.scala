package database

import models.{Category, Season, Recipe => ModelRecipe}

final case class Recipe(id: Int, name: String, category: String, season: String) {
  def toModelRecipe(ingredients: Seq[Ingredient]): ModelRecipe = ModelRecipe(
    id, name, ingredients.map(_.toModelIngredient), Category.category(category).get, Season.season(season).get
  )
}
