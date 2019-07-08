package components

import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.html

object backToMenu {

  def apply(): ReactiveHtmlElement[html.Anchor] = a(href := "/", "Back to menu")

}
