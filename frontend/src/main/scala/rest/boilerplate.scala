package rest

import java.nio.ByteBuffer

import fr.hmil.roshttp.{HttpRequest, Method}
import fr.hmil.roshttp.Protocol.HTTP
import fr.hmil.roshttp.body.BulkBodyPart
import fr.hmil.roshttp.response.SimpleHttpResponse
import main.Main
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

  def postObject[T](path: String, o: T)(implicit scheduler: Scheduler, writer: Writer[T]): Future[SimpleHttpResponse] =
    apply().withPath(path).withMethod(Method.POST)
    .withBody(new BulkBodyPart {
      def contentData: ByteBuffer = ByteBuffer.wrap(write(o).getBytes("utf-8"))
      def contentType: String = "application/json; charset=utf-8"
    })
    .withHeader("Csrf-Token", Main.maybeToken.get.value)
    .send()

}
