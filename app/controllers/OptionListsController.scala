package controllers

import javax.inject.Inject
import models.{Category, Ingredient, MeasureUnit, RecipeDBModel, Season}
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.libs.json._
import utils.json.UpickleJson

import scala.concurrent.ExecutionContext


final class OptionListsController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                            recipeDBModel: RecipeDBModel, cc: ControllerComponents)
                                           (implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit def seasonWrites: Writes[Seq[Season]] = UpickleJson.writes[Seq[Season]]
  implicit def categoryWrites: Writes[Seq[Category]] = UpickleJson.writes[Seq[Category]]
  implicit def unitsWrites: Writes[Seq[MeasureUnit]] = UpickleJson.writes[Seq[MeasureUnit]]
  implicit def ingredientWrites: Writes[Seq[Ingredient]] = UpickleJson.writes[Seq[Ingredient]]

  def seasons: Action[AnyContent] = Action { Ok(Json.toJson(Season.seasons)) }
  def categories: Action[AnyContent] = Action { Ok(Json.toJson(Category.categories)) }
  def units: Action[AnyContent] = Action { Ok(Json.toJson(MeasureUnit.units)) }
  def possibleIngredients: Action[AnyContent] = Action.async {
    recipeDBModel.listOfPossibleIngredients.map(Json.toJson(_)).map(Ok(_))
  }

  def optionList(name: String): Action[AnyContent] = name match {
    case "seasons" => seasons
    case "categories" => categories
    case "units" => units
    case "ingredients" => possibleIngredients
    case _ => Action{ NotFound }
  }

}
