package utils.database.tables

import database.Recipe
import slick.lifted.Tag
import utils.database.DBProfile.api._

final class RecipeTable(tag: Tag) extends Table[Recipe](tag, "recipe") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Unique)
  def category = column[String]("category")
  def season = column[String]("season")
  def description = column[String]("description", O.Length(65536))

  def * = (id, name, category, season, description) <> (Recipe.tupled, Recipe.unapply)

}

object RecipeTable {

  def query: TableQuery[RecipeTable] = TableQuery[RecipeTable]

}
