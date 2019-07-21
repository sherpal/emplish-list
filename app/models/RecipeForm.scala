package models

object RecipeForm {
  import play.api.data.Forms._
  import play.api.data.Form
  import play.api.data.format.Formats.doubleFormat


  /**
    * (
    * id: Int, name: String,
    * ingredients: Seq[Ingredient],
    * category: Category, season: Season,
    * description: String
    * )
    */

  private val unitMapper = mapping("name" -> nonEmptyText)(MeasureUnit.apply)(MeasureUnit.unapply)

  private val ingredientMapping = mapping(
    "id" -> number(min = 0),
    "name" -> nonEmptyText,
    "qt" -> of[Double],
    "unit" -> unitMapper
  )(Ingredient.apply)(Ingredient.unapply)

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
  )

}
