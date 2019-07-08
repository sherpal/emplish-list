package index

import main.Main
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.html
import rest.RecipeCalls

object IndexMain {

  def element: ReactiveHtmlElement[html.Div] = div(
    h1(Main.title),
    button("New"), // todo
    ul(
      children <-- RecipeCalls.lisDBRecipes
    )
  )

  render(Main.root, element)

}
