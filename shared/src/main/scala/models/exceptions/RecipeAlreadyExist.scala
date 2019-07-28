package models.exceptions

final class RecipeAlreadyExist(val name: String) extends Exception(s"Recipe `$name` already exists.")
