package components.form

import com.raquo.laminar.api.L._
import components.form.LaminarForm._
import models.{Ingredient, Recipe}


object RecipeForm {

  def apply(possibleIngredients: List[Ingredient], recipe: Recipe): Element = {
    val ingredientInputs: Var[List[Element]] = Var(
      recipe.ingredients.zipWithIndex.toList.map { case (ingredient, idx) =>
          ingredientInput(idx, possibleIngredients, Some(ingredient))
      }
    )

    div(
      inputText("name")(displayName = "Recipe name"),

      div(id := "ingredients-table",
        table(
          thead(tr(th("Ingredients"))),
          tbody(children <-- ingredientInputs.signal)
        ),
        button("New Ingredient",
          onClick.preventDefault.stopPropagation.mapTo(ingredientInputs.now)
            .map(ls => ls :+ ingredientInput(ls.length, possibleIngredients))
            --> ingredientInputs.writer
        )
      ),

      seasonInput(recipe.season), categoryInput(recipe.category),

      descriptionInput(recipe.description)
    )
  }


}
