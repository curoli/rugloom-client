package rugloom.web.socket

import play.api.libs.json.{JsValue, JsObject, JsString, Json, Writes}
import rugloom.web.socket.Message.Kind

import scala.tools.nsc.interpreter.Results.Result

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
    override def writes(message: PingMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind
    )
  }

  implicit val echoMessageWrites = new Writes[EchoMessage] {
    override def writes(message: EchoMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind,
      "inResponseToId" -> message.inResponseToId
    )
  }

  implicit val lineEnteredMessageWrites = new Writes[LineEnteredMessage] {
    override def writes(message: LineEnteredMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind,
      "line" -> message.line,
      "num" -> message.num
    )
  }

  implicit val resultReturnedWrites = new Writes[Result] {
    override def writes(result: Result): JsString = new JsString(result.toString)
  }

  implicit val shellResponseMessageWrites = new Writes[ShellResponseMessage] {
    override def writes(message: ShellResponseMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind,
      "inResponseToId" -> message.inResponseToId,
      "num" -> message.response.num,
      "line" -> message.response.lineEntered,
      "resultReturned" -> message.response.resultReturned,
      "resultLine" -> message.response.resultLineOpt,
      "linesConsole" -> message.response.linesConsole
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
