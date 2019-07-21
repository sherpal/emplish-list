package models.exceptions

final class RecipeDoesNotExist(val name: String) extends Exception(s"Recipe `$name` does not exist in DB.")
