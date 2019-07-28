package components.form

import com.raquo.laminar.api.L._
import com.raquo.laminar.lifecycle.NodeDidMount
import org.scalajs.dom.{html => domhtml}
import models.{Category, Ingredient, Season}

object LaminarForm {

  def inputText(inputName: String)(inputId: String = inputName, displayName: String = inputName): Element = dl(
    dt(
      label(forId := inputId, displayName)
    ),
    dd(
      input(`type` := "text", name := inputName, id := inputId)
    )
  )

  def hiddenInputText(inputName: String, content: String): Element = input(
    `type` := "text", hidden := true, name := inputName, value := content
  )

  def disabledInputText(inputName: String, content: String): Element = input(
    `type` := "text", disabled := true, name := inputName, value := content
  )

  def selectInput(inputName: String)
                 (inputId: String = inputName.replaceAll("""\.""", "_"),
                  displayName: String,
                  options: List[(String, String)]): Element = dl(
    dt(label(forId := inputId, displayName)),
    dd(select(id := inputId, name := inputName, options.map {
      case (v, display) => option(value := v, display)
    }))
  )

  def textAreaInput(inputName: String)
                   (inputId: String = inputName.replaceAll("""\.""", "_"),
                    displayName: String): Element = dl(
    dt(label(forId := inputId, displayName)),
    dd(textArea(id := inputId, name := inputName))
  )

  def ingredientInput(
                       idx: Int, possibleIngredients: List[Ingredient],
                       maybeStartingIngredient: Option[Ingredient] = None
                     ): Element = {
    if (possibleIngredients.isEmpty) tr()
    else {

      val ingredients = maybeStartingIngredient match {
        case Some(startingIngredient) => startingIngredient +: possibleIngredients.filterNot(
            _.name == startingIngredient.name
          )
        case None =>
          possibleIngredients
      }

      val ingredientMap = ingredients.map(i => i.name -> i).toMap
      val currentIngredient = Var(ingredients.head)
      val nameSelect = select(id := s"ingredients_${idx}_name", name := s"ingredients[$idx].name",
        ingredients.map(_.name).map(n => option(value := n, n)),
        inContext(thisNode =>
          onChange.mapTo(thisNode.ref.value).map(ingredientMap) --> currentIngredient.writer
        )
      )
      val unitName = span(child <-- currentIngredient.signal.map(_.unit.name), input(
        `type` := "text",
        hidden := true,
        name := s"ingredients[$idx].unit.name",
        id := s"ingredients_${idx}_unit_name",
        value <-- currentIngredient.signal.map(_.unit.name)
      ))

      val quantity = input(
        `type` := "number",
        name := s"ingredients[$idx].qt",
        id := s"ingredients_${idx}_qt"
      )

      val idHiddenInput = input(
        `type` := "number", name := s"ingredients[$idx].id",
        id := s"ingredients_${idx}_id",
        value := idx.toString,
        hidden := true
      )

      tr(td(nameSelect), td(quantity), td(unitName, idHiddenInput))
    }
  }

  def seasonInput(season: Season): Element = {
    val elem = selectInput("season.name")(
      displayName = "Season", options = Season.seasons.map(_.name).map(n => (n, n)).toList
    )

    elem.ref.lastChild.firstChild.asInstanceOf[domhtml.Select].value = season.name

    elem
  }

  def categoryInput(category: Category): Element = {
    val elem = selectInput("category.name")(
      displayName = "Category", options = Category.categories.map(_.name).map(n => (n, n)).toList
    )

    elem.ref.lastChild.firstChild.asInstanceOf[domhtml.Select].value = category.name

    elem
  }

  def descriptionInput(description: String): Element = {
    val elem = textAreaInput("description")(displayName = "Description")

    elem.ref.lastChild.firstChild.asInstanceOf[domhtml.TextArea].value = description

    elem
  }

}
