package database

import models.{Ingredient => ModelIngredient, MeasureUnit}

final case class Ingredient(id: Int, name: String, unit: String, recipeId: Int) {
  def toModelIngredient: ModelIngredient = ModelIngredient(id, name, MeasureUnit.unit(unit).get)
}
