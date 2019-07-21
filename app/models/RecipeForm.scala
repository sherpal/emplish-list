package models

object RecipeForm {
  import play.api.data.Forms._
  import play.api.data.Form
  import play.api.data.format.Formats.doubleFormat

  private val unitMapper = mapping("name" -> nonEmptyText)(MeasureUnit.apply)(MeasureUnit.unapply)

  private val ingredientMapping = mapping(
    "id" -> number(min = 0),
    "name" -> nonEmptyText,
    "qt" -> of[Double],
    "unit" -> unitMapper
  )(Ingredient.apply)(Ingredient.unapply)
    .verifying("Quantity of ingredients must be positive", _.qt > 0)

  private val seasonMapper = mapping("name" -> nonEmptyText)(Season.apply)(Season.unapply)

  private val categoryMapper = mapping("name" -> nonEmptyText)(Category.apply)(Category.unapply)

  val form = Form(
    mapping(
      "id" -> number(min = 0),
      "name" -> nonEmptyText,
      "ingredients" -> seq(ingredientMapping),
      "category" -> categoryMapper, "season" -> seasonMapper,
      "description" -> text
    )(Recipe.apply)(Recipe.unapply)
      .verifying(
        "Ingredients must be distinct", !_.ingredients.groupBy(_.name).exists(_._2.tail.nonEmpty)
      )
  )

}
