package rest

import com.raquo.airstream.features.FlattenStrategy
import com.raquo.laminar.api.L._
import components.rowRecipe
import models.{Recipe => ModelRecipe}
import database.{Recipe => DBRecipe}
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler.Implicits.global
import upickle.default._

object RecipeCalls {

  /** Weird airstream business. */
  implicit private val strategy: FlattenStrategy.ConcurrentFutureStrategy.type = ConcurrentFutureStrategy

  def dbRecipes: EventStream[Seq[DBRecipe]] = EventStream.fromFuture(
    boilerplate().withPath("/v1/rest/db-recipes-list")
    .send().map(_.body).map(read[Seq[DBRecipe]](_))
  )

  def lisDBRecipes: EventStream[List[Element]] = dbRecipes.map(_.toList.map(rowRecipe.apply))

  def postRecipe(recipe: ModelRecipe): EventStream[SimpleHttpResponse] = EventStream.fromFuture(
    boilerplate.postObject("/v1/rest/new-recipe", recipe)
  )

  def deleteRecipe(recipeId: Int): EventStream[SimpleHttpResponse] = EventStream.fromFuture(
    boilerplate.postObject(s"/delete-recipe/$recipeId", "")
  )

}
