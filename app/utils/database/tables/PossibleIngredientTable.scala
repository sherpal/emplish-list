package utils.database.tables

import database.Ingredient
import models.MeasureUnit.{Item, Liter}
import models.{Ingredient => ModelIngredient}
import slick.lifted.Tag
import utils.database.DBProfile.api._


final class PossibleIngredientTable(tag: Tag) extends Table[Ingredient](tag, "possible_ingredient") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name")
  def unit = column[String]("unit")

  def * = (id, name, unit) <> (
    (t: (Int, String, String)) => Ingredient(t._1, t._2, 0.0, t._3, 0),
    Ingredient.unapply(_: Ingredient).map { case (i, n, _, u, _) => (i, n, u) }
  )

}

object PossibleIngredientTable {

  def query: TableQuery[PossibleIngredientTable] = TableQuery[PossibleIngredientTable]

  def ingredients: Seq[Ingredient] = Seq(
    ModelIngredient(0, "Cheddar", 0.0,  Item),
    ModelIngredient(0, "Patates", 0.0, Item),
    ModelIngredient(0, "Lait", 0.0, Liter)
  ).map{ case ModelIngredient(id, name, qt, unit) => Ingredient(id, name, qt, unit.name, 0) }

}
