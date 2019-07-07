package models

final case class Ingredient(id: Int, name: String, unit: MeasureUnit) extends Ordered[Ingredient] {
  def compare(that: Ingredient): Int = this.name compare that.name
}

object Ingredient {
  import upickle.default.{ReadWriter, macroRW}
  implicit final val readWriter: ReadWriter[Ingredient] = macroRW
}
