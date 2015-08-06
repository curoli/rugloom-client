package rugloom.web.socket

import play.api.libs.json.{JsObject, JsString, JsValue, Json, Writes}
import rugloom.shell.ShellOutput
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

  implicit val inputMessageWrites = new Writes[InputMessage] {
    override def writes(message: InputMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind,
      "input" -> message.input,
      "num" -> message.num
    )
  }

  implicit val resultReturnedWrites = new Writes[Result] {
    override def writes(result: Result): JsString = new JsString(result.toString)
  }

  implicit val shellOutputWrites = new Writes[ShellOutput] {
    override def writes(response: ShellOutput): JsObject = Json.obj(
      "kind" -> response.kindAsString,
      "text" -> response.text
    )
  }

  implicit val shellOutputMessageWrites = new Writes[ShellOutputMessage] {
    override def writes(message: ShellOutputMessage): JsObject = Json.obj(
      "id" -> message.id,
      "kind" -> message.kind,
      "output" -> message.output
    )
  }

  implicit val messageWrites = new Writes[Message] {
    override def writes(message: Message): JsValue = message match {
      case pingMessage: PingMessage => Json.toJson(pingMessage)
      case echoMessage: EchoMessage => Json.toJson(echoMessage)
      case lineEnteredMessage: InputMessage => Json.toJson(lineEnteredMessage)
    }
  }

}
