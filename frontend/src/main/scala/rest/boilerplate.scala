package rest

import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Protocol.HTTP
import monix.execution.Scheduler
import org.scalajs.dom
import upickle.default._

import scala.concurrent.Future

private[rest] object boilerplate {

  def apply(): HttpRequest = HttpRequest()
    .withHost(dom.document.location.hostname)
    .withPort(dom.document.location.port.toInt)
    .withProtocol(HTTP)

  def call[T](path: String)(implicit scheduler: Scheduler, reader: ReadWriter[T]): Future[T] =
    apply().withPath(path).send().map(_.body).map(read[T](_))

}
