package models

import database.{Ingredient => DBIngredient, Recipe => DBRecipe}

final case class Recipe(id: Int, name: String, ingredients: Seq[Ingredient], category: Category, season: Season) {
  def toDBRecipe: (DBRecipe, Seq[DBIngredient]) = (
    DBRecipe(id, name, category.name, season.name),
    ingredients.map(i => DBIngredient(i.id, i.name, i.unit.name, id))
  )
}

object Recipe {
  import upickle.default.{ReadWriter, macroRW}
  implicit final val readWriter: ReadWriter[Recipe] = macroRW
}
