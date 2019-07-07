package controllers

import javax.inject.Inject
import models.{Category, MeasureUnit, Season}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.libs.json._
import utils.json.UpickleJson


final class OptionListsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit def seasonWrites: Writes[Seq[Season]] = UpickleJson.writes[Seq[Season]]
  implicit def categoryWrites: Writes[Seq[Category]] = UpickleJson.writes[Seq[Category]]
  implicit def unitsWrites: Writes[Seq[MeasureUnit]] = UpickleJson.writes[Seq[MeasureUnit]]

  def seasons: Action[AnyContent] = Action { Ok(Json.toJson(Season.seasons)) }
  def categories: Action[AnyContent] = Action { Ok(Json.toJson(Category.categories)) }
  def units: Action[AnyContent] = Action { Ok(Json.toJson(MeasureUnit.units)) }

  def optionList(name: String): Action[AnyContent] = name match {
    case "seasons" => seasons
    case "categories" => categories
    case "units" => units
    case _ => Action{ NotFound }
  }

}
