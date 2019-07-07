package utils.database.tables

import database.Ingredient
import slick.lifted.Tag
import utils.database.DBProfile.api._


final class PossibleIngredientTable(tag: Tag) extends Table[Ingredient](tag, "possible_ingredient") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name")
  def unit = column[String]("unit")

  def * = (id, name, unit, 0) <> (Ingredient.tupled, Ingredient.unapply)

}

object PossibleIngredientTable {

  def query: TableQuery[PossibleIngredientTable] = TableQuery[PossibleIngredientTable]

}
