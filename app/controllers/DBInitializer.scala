package controllers

import javax.inject.Inject
import models.DatabaseInitializer
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc._

import scala.concurrent.ExecutionContext

final class DBInitializer @Inject()(initializer: DatabaseInitializer, cc: ControllerComponents)
                                   (implicit ec: ExecutionContext) extends AbstractController(cc)  {

  def initialize(): Action[AnyContent] = Action.async {
    initializer.initialize().map(_ => Ok("Initialized"))
  }

  def reset(): Action[AnyContent] = Action.async {
    initializer.reset().map(_ => Ok("db reset"))
  }

  def insertIngredients(): Action[AnyContent] = Action.async {
    initializer.insertIngredients().map(_ => Ok("ingredients inserted"))
  }

  def deleteIngredients(): Action[AnyContent] = Action.async {
    initializer.resetIngredients().map(_ => Ok("ingredients reset"))
  }

}
