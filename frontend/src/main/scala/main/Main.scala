package main

import index.IndexMain
import newrecipe.NewRecipeMain
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLElement
import rest.Token
import utils.constants.Constants

import scala.util.Try


object Main {

  lazy val title: String = dom.document.title
  lazy val root: html.Div = dom.document.getElementById("root").asInstanceOf[html.Div]

  def elementById[T <: HTMLElement](id: String): T = dom.document.getElementById(id).asInstanceOf[T]

  lazy val maybeToken: Option[Token] =
    Try(Token(GlobalScope.tokenName, GlobalScope.tokenValue)).map(Some(_)).getOrElse(None)


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
