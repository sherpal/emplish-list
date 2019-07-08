package main

import index.IndexMain
import org.scalajs.dom
import org.scalajs.dom.html
import utils.constants.Constants

object Main {

  lazy val title: String = dom.document.title
  lazy val root: html.Div = dom.document.getElementById("root").asInstanceOf[html.Div]

  def main(args: Array[String]): Unit = {

    title match {
      case Constants.`welcome to emplish list` =>
        IndexMain
      case _ =>
        dom.console.error(s"Unknown title: $title")
    }

  }

}
