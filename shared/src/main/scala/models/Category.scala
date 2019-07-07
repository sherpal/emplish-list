package models

import upickle.default._

sealed trait Category extends Ordered[Category] {
  final def name: String = toString
  final def compare(that: Category): Int = this.name compare that.name
}

object Category {
  case object Desert extends Category
  case object MainCourse extends Category

  def categories: Seq[Category] = Seq[Category](Desert, MainCourse).sorted
  def category(name: String): Option[Category] = categories.find(name == _.name)

  implicit final val readWriter: ReadWriter[Category] = readwriter[String].bimap(_.name, category(_).get)
}
