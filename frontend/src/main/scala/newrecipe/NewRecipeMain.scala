package newrecipe

import com.raquo.laminar.api.L._
import _root_.main.Main.elementById
import components.backToMenu
import components.form.LaminarForm._
import components.form.RecipeForm
import models.{Ingredient, Recipe}
import org.scalajs.dom.html
import rest.OptionsListCalls

object NewRecipeMain {

  private implicit val owner: Owner = new Owner {}

  private val nav = elementById[html.Div]("nav")

  render(nav, backToMenu())

  def ingredient(idx: Int): Element =
    tr(td(inputText(s"ingredients[$idx]")(inputId = s"ingredients_$idx", displayName = s"ingredients.$idx")))

  def element(possibleIngredients: List[Ingredient]): Element = RecipeForm(possibleIngredients, Recipe.empty)

  OptionsListCalls.ingredients.map(_.toList).foreach(
    ls => render(elementById[html.Div]("ingredients-form"), element(ls))
  )

}
