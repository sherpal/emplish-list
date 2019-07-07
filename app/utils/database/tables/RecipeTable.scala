package utils.database.tables

import database.Recipe
import slick.lifted.Tag
import utils.database.DBProfile.api._

final class RecipeTable(tag: Tag) extends Table[Recipe](tag, "recipe") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def category = column[String]("category")
  def season = column[String]("season")

  def * = (id, name, category, season) <> (Recipe.tupled, Recipe.unapply)

}

object RecipeTable {

  def query: TableQuery[RecipeTable] = TableQuery[RecipeTable]

}
