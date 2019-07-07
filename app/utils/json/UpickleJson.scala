package utils.json


import play.api.libs.json._
import upickle.default.{Writer => UpickleWriter, Reader => UpickleReader, read, write}

object UpickleJson {

  def reads[A](implicit reader: UpickleReader[A]): Reads[A] = (json: JsValue) => {
    try {
      val value = read[A](json.toString)
      JsSuccess(value)
    } catch {
      case _: Throwable =>
        JsError()
    }
  }

  def writes[A](implicit writer: UpickleWriter[A]): Writes[A] = (o: A) => Json.parse(write(o))

}
