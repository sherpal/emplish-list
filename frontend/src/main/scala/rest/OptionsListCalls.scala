package rest

import com.raquo.laminar.api.L._
import models.{Category, Ingredient, Season}
import monix.execution.Scheduler.Implicits.global

object OptionsListCalls {

  def seasons: EventStream[Seq[Season]] = EventStream.fromFuture(
    boilerplate.call[Seq[Season]]("/options-lists/seasons")
  )
  def categories: EventStream[Seq[Category]] = EventStream.fromFuture(
    boilerplate.call[Seq[Category]]("/options-lists/categories")
  )
  def ingredients: EventStream[Seq[Ingredient]] = EventStream.fromFuture(
    boilerplate.call[Seq[Ingredient]]("/options-lists/ingredients")
  )

}
