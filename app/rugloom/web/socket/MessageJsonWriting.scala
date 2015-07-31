package rugloom.web.socket

import play.api.libs.json.{JsValue, JsObject, JsString, Json, Writes}
import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/30/2015.
 */
object MessageJsonWriting {

  implicit val timedIdWrites = new Writes[TimedId] {
    override def writes(id: TimedId): JsObject = Json.obj(
      "time" -> id.time,
      "rand" -> id.rand
    )
  }

  implicit val messageKindWrites = new Writes[Message.Kind] {
    override def writes(kind: Kind): JsString = JsString(kind.toString)
  }

  implicit val pingMessageWrites = new Writes[PingMessage] {
    override def writes(pingMessage: PingMessage): JsObject = Json.obj(
      "id" -> pingMessage.id,
      "kind" -> pingMessage.kind
    )
  }

  implicit val echoMessageWrites = new Writes[EchoMessage] {
    override def writes(echoMessage: EchoMessage): JsObject = Json.obj(
      "id" -> echoMessage.id,
      "kind" -> echoMessage.kind,
      "inResponseToId" -> echoMessage.inResponseToId
    )
  }

  implicit val lineEnteredMessageWrites = new Writes[LineEnteredMessage] {
    override def writes(lineEnteredMessage: LineEnteredMessage): JsObject = Json.obj(
      "id" -> lineEnteredMessage.id,
      "kind" -> lineEnteredMessage.kind,
      "line" -> lineEnteredMessage.line,
      "num" -> lineEnteredMessage.num
    )
  }

  implicit val messageWrites = new Writes[Message] {
    override def writes(message: Message): JsValue = message match {
      case pingMessage: PingMessage => Json.toJson(pingMessage)
      case echoMessage: EchoMessage => Json.toJson(echoMessage)
      case lineEnteredMessage: LineEnteredMessage => Json.toJson(lineEnteredMessage)
    }
  }

}
