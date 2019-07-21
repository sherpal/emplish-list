package newrecipe

import com.raquo.laminar.api.L._
import _root_.main.Main.elementById
import components.backToMenu
import components.form.LaminarForm._
import models.Ingredient
import org.scalajs.dom.html
import rest.OptionsListCalls

object NewRecipeMain {

  private implicit val owner: Owner = new Owner {}

  private val nav = elementById[html.Div]("nav")

  render(nav, backToMenu())

  def ingredient(idx: Int): Element =
    tr(td(inputText(s"ingredients[$idx]")(inputId = s"ingredients_$idx", displayName = s"ingredients.$idx")))

  def element(possibleIngredients: List[Ingredient]): Element = {
    val ingredientInputs: Var[List[Element]] = Var(Nil)

    div(
      table(
        thead(tr(th("Ingredients"))),
        tbody(children <-- ingredientInputs.signal)
      ),
      button("New Ingredient",
        onClick.preventDefault.stopPropagation.mapTo(ingredientInputs.now)
          .map(ls => ls :+ ingredientInput(ls.length, possibleIngredients))
          --> ingredientInputs.writer
      )
    )
  }

  OptionsListCalls.ingredients.map(_.toList).foreach(
    ls => render(elementById[html.Div]("ingredients-form"), element(ls))
  )

//  implicit val owner: L.Owner = new Owner {}
//
//  private val recipe = Var[Recipe](Recipe(0, "", Seq(), null, null, ""))
//
//  ingredientList.$ingredients.foreach(ingredients => recipe.update(_.copy(ingredients = ingredients)))
//
//  def element: ReactiveHtmlElement[html.Div] = {
//    val categories = OptionsListCalls.categories
//    val categorySelect = select(
//      inContext(thisNode =>
//        onChange.mapTo(thisNode.ref.value).map(Category.category(_).get).map(cat => recipe.now.copy(category = cat))
//          --> recipe.writer
//      ),
//      children <-- categories.map(_.toList.map(cat => option(cat.name)))
//    )
//    categories.map(_.head).map(cat => recipe.now.copy(category = cat)).foreach(recipe.set)
//
//    val seasons = OptionsListCalls.seasons
//    val seasonSelect = select(
//      inContext(thisNode =>
//        onChange.mapTo(thisNode.ref.value).map(Season.season(_).get).map(s => recipe.now.copy(season = s))
//         --> recipe.writer
//      ),
//      children <-- seasons.map(_.toList.map(s => option(s.name)))
//    )
//    seasons.map(_.head).map(s => recipe.now.copy(season = s)).foreach(recipe.set)
//
//    div(
//      h1(Main.title),
//      backToMenu(),
//      h2("Fill recipe information below"),
//      form(
//        div(
//          label("Recipe name "),
//          input(
//            `type` := "text", placeholder := "name",
//            inContext(thisNode =>
//              onInput.mapTo(thisNode.ref.value).map(name => recipe.now.copy(name = name)) --> recipe.writer
//            )
//          )
//        ),
//        div(label("Category "), categorySelect),
//        div(label("Season "), seasonSelect),
//        child <-- OptionsListCalls.ingredients.map(_.toList).map(ingredientList.apply(recipe, _)),
//        div(
//          h3("Description"),
//          textArea(
//            width := "400px", height := "300px",
//            inContext(thisNode =>
//              onInput.mapTo(thisNode.ref.value).map(desc => recipe.now.copy(description = desc)) --> recipe.writer
//            )
//          )
//        ),
//        onSubmit.mapTo(recipe.now).preventDefault --> { r => RecipeCalls.postRecipe(r) }
//      ),
//      pre(
//        child <-- recipe.signal.map(write[Recipe](_, indent = 2))
//      )
//    )
//  }

}
