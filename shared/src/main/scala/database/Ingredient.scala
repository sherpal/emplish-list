package database

import models.{Ingredient => ModelIngredient, MeasureUnit}

final case class Ingredient(id: Int, name: String, qt: Double, unit: String, recipeId: Int) {
  def toModelIngredient: ModelIngredient = ModelIngredient(id, name, qt, MeasureUnit.unit(unit).get)
}
