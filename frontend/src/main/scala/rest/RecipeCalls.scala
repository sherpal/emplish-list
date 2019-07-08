package rest

import com.raquo.airstream.features.FlattenStrategy
import com.raquo.laminar.api.L._
import database.{Recipe => DBRecipe}
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Protocol.HTTP
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom
import upickle.default._

import scala.concurrent.Future

object RecipeCalls {

  /** Weird airstream business. */
  implicit private val strategy: FlattenStrategy.ConcurrentFutureStrategy.type = ConcurrentFutureStrategy

  /**
    * Some boilerplate for http requests.
    */
  private val boilerPlate: HttpRequest = HttpRequest()
    .withHost(dom.document.location.hostname)
    .withPort(dom.document.location.port.toInt)
    .withProtocol(HTTP)

  implicit private class FutureToStream[T](f: Future[T]) {
    def stream: EventStream[T] = EventStream.fromFuture(f)
  }

  def dbRecipes: EventStream[Seq[DBRecipe]] = boilerPlate.withPath("/v1/rest/db-recipes-list")
    .send().map(_.body).map(read[Seq[DBRecipe]](_)).stream

  def lisDBRecipes: EventStream[List[Element]] = dbRecipes.map(_.toList.map(r => li(r.name)))

}
