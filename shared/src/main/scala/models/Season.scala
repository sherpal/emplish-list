package models

import upickle.default._

sealed trait Season {
    final def name: String = toString
}

object Season {
  case object Winter extends Season
  case object Spring extends Season
  case object Summer extends Season
  case object Autumn extends Season
  case object AllYear extends Season

  def seasons: Seq[Season] = Seq(AllYear, Winter, Spring, Summer, Autumn)
  def season(name: String): Option[Season] = seasons.find(name == _.name)

  def apply(name: String): Season = season(name).get

  def unapply(arg: Season): Option[String] = Some(arg.name)

  implicit val seasonReadWriter: ReadWriter[Season] = readwriter[String].bimap(_.name, season(_).get)
}
