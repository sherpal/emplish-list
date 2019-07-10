package components

import com.raquo.laminar.api.L._
import models.{Ingredient, Recipe}

object ingredientList {

  implicit private val owner: Owner = new Owner {}

  private val ingredients: Var[List[Ingredient]] = Var(Nil)

  def $ingredients: StrictSignal[List[Ingredient]] = ingredients.signal

  def apply(recipe: Var[Recipe], possibleIngredients: List[Ingredient]): Element = {

    def availableIngredients = possibleIngredients.filterNot(i => ingredients.now.exists(_.name == i.name))

    def row(ingredient: Ingredient): Element = div(
      ingredient.name, " (", ingredient.qt.toString, ", ", ingredient.unit.name, ")",
      button("delete", onClick.mapTo(ingredients.now.filterNot(_.name == ingredient.name)) --> ingredients.writer)
    )

    val currentIngredient: Var[Option[Ingredient]] = Var(None)

    def newIngredient(ingredient: Ingredient): Element = {
      span(
        display <-- currentIngredient.signal.map(_.isDefined).map(if (_) "inline" else "none"),
        "Quantity: ",
        input(`type` := "number", width := "50px", value := ingredient.qt.toString,
          inContext(thisNode =>
            onChange.mapTo(thisNode.ref.valueAsNumber).map(value => currentIngredient.now.map(_.copy(qt = value)))
              --> currentIngredient.writer
          )
        ),
        label(ingredient.unit.name),
        button(
          "add", onClick.mapTo(currentIngredient.now).filter(_.isDefined).map(_.get)
            .map(ingredients.now :+ _) --> ingredients.writer
        )
      )
    }

    def newRow: Element = {
      val choices = availableIngredients.map(i => option(i.name))
      currentIngredient.set(None)

      div(
        select(
          choices,
          inContext(thisNode =>
            onChange.mapTo(thisNode.ref.value).map(name => possibleIngredients.find(_.name == name))
              --> currentIngredient.writer
          )
        ),
        child <-- currentIngredient.signal.map {
          case Some(ingredient) => newIngredient(ingredient)
          case None => span()
        }
      )
    }

    div(
      children <-- ingredients.signal.map(_.map(row)),
      child <-- ingredients.signal.map(_ => newRow)
    )

  }

}
