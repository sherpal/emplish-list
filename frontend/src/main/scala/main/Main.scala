package main

import index.IndexMain
import newrecipe.NewRecipeMain
import org.scalajs.dom
import org.scalajs.dom.html
import utils.constants.Constants

object Main {

  lazy val title: String = dom.document.title
  lazy val root: html.Div = dom.document.getElementById("root").asInstanceOf[html.Div]

  def main(args: Array[String]): Unit = {

    dom.console.log(title)

    title match {
      case Constants.`welcome to emplish list` => IndexMain
      case Constants.`new recipe`              => NewRecipeMain
      case _ =>
        dom.console.error(s"Unknown title: $title")
    }

  }

}
