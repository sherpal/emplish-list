package components.form

import com.raquo.laminar.api.L._
import models.Ingredient

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

  def selectInput(inputName: String)(inputId: String = inputName, displayName: String, options: List[(String, String)]): Element = dl(
    dt(label(forId := inputId, displayName)),
    dd(select(id := inputId, name := inputName, options.map {
      case (v, display) => option(value := v, display)
    }))
  )

  def ingredientInput(idx: Int, possibleIngredients: List[Ingredient]): Element = {
    if (possibleIngredients.isEmpty) tr()
    else {
      val ingredientMap = possibleIngredients.map(i => i.name -> i).toMap
      val currentIngredient = Var(possibleIngredients.head)
      val nameSelect = select(id := s"ingredients_${idx}_name", name := s"ingredients[$idx].name",
        possibleIngredients.map(_.name).map(n => option(value := n, n)),
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

}
