package newrecipe

import com.raquo.laminar.api.L._
import _root_.main.Main
import com.raquo.laminar.api.L
import com.raquo.laminar.nodes.ReactiveHtmlElement
import components.backToMenu
import models.{Category, Recipe, Season}
import org.scalajs.dom.html
import rest.OptionsListCalls
import upickle.default._

object NewRecipeMain {

  implicit val owner: L.Owner = new Owner {}

  private val recipe = Var[Recipe](Recipe(0, "", Seq(), null, null, ""))

  def element: ReactiveHtmlElement[html.Div] = {
    val categories = OptionsListCalls.categories
    val categorySelect = select(
      inContext(thisNode =>
        onChange.mapTo(thisNode.ref.value).map(Category.category(_).get).map(cat => recipe.now.copy(category = cat))
          --> recipe.writer
      ),
      children <-- categories.map(_.toList.map(cat => option(cat.name)))
    )
    categories.map(_.head).map(cat => recipe.now.copy(category = cat)).foreach(recipe.set)

    val seasons = OptionsListCalls.seasons
    val seasonSelect = select(
      inContext(thisNode =>
        onChange.mapTo(thisNode.ref.value).map(Season.season(_).get).map(s => recipe.now.copy(season = s))
         --> recipe.writer
      ),
      children <-- seasons.map(_.toList.map(s => option(s.name)))
    )
    seasons.map(_.head).map(s => recipe.now.copy(season = s)).foreach(recipe.set)

    div(
      h1(Main.title),
      backToMenu(),
      h2("Fill recipe information below"),
      form(
        div(
          label("Recipe name "),
          input(
            `type` := "text", placeholder := "name",
            inContext(thisNode =>
              onInput.mapTo(thisNode.ref.value).map(name => recipe.now.copy(name = name)) --> recipe.writer
            )
          )
        ),
        div(label("Category "), categorySelect),
        div(label("Season "), seasonSelect),
        div(
          h3("Description"),
          textArea(
            width := "400px", height := "300px",
            inContext(thisNode =>
              onInput.mapTo(thisNode.ref.value).map(desc => recipe.now.copy(description = desc)) --> recipe.writer
            )
          )
        )
      ),
      pre(
        child <-- recipe.signal.map(write[Recipe](_, indent = 2))
      )
    )
  }

  render(Main.root, element)

}
