package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.ControllerComponents

import scala.concurrent.ExecutionContext

final class NewRecipeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                                         (implicit ec: ExecutionContext) {



}
