package models

import upickle.default._

sealed trait MeasureUnit extends Ordered[MeasureUnit] {
  final def name: String = toString
  def compare(that: MeasureUnit): Int = this.name compare that.name
}

object MeasureUnit {
  case object Item extends MeasureUnit
  case object Gr extends MeasureUnit
  case object Liter extends MeasureUnit

  def units: Seq[MeasureUnit] = Seq[MeasureUnit](Item, Gr, Liter).sorted
  def unit(name: String): Option[MeasureUnit] = units.find(name == _.name)

  def apply(name: String): MeasureUnit = unit(name).get

  def unapply(arg: MeasureUnit): Option[String] = Some(arg.name)

  implicit final val readWriter: ReadWriter[MeasureUnit] = readwriter[String].bimap(_.name, unit(_).get)
}
