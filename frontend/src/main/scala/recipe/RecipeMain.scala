package recipe

import com.raquo.airstream.ownership.Owner
import main.Main.elementById
import org.scalajs.dom
import org.scalajs.dom.html
import rest.RecipeCalls

import scala.scalajs.js

object RecipeMain {

  implicit private val owner: Owner = new Owner {}


  final val deleteRecipe = elementById[html.Button]("delete")

  final lazy val recipeId = dom.window.asInstanceOf[js.Dynamic].recipeId.asInstanceOf[Int]

  deleteRecipe.onclick = (_: dom.Event) => {
    RecipeCalls.deleteRecipe(recipeId).foreach(_ => dom.window.location.href = "/")
  }

}
