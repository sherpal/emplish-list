package components

import com.raquo.laminar.api.L._
import database.Recipe

object rowRecipe {

  def apply(recipe: Recipe): Element = li(a(href := s"/recipe/${recipe.name}", recipe.name))

}
