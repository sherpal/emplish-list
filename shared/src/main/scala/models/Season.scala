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

  def seasons: Seq[Season] = Seq(Winter, Spring, Summer, Autumn)
  def season(name: String): Option[Season] = seasons.find(name == _.name)

  implicit val seasonReadWriter: ReadWriter[Season] = readwriter[String].bimap(_.name, season(_).get)
}
