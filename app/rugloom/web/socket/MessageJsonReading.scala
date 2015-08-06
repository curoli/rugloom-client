package rugloom.web.socket

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, functionalCanBuildApplicative}
import play.api.libs.json.{JsSuccess, JsError, JsPath, JsResult, JsString, JsValue, Reads}
import rugloom.util.MaybeToJsResult
import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/30/2015.
 */
object MessageJsonReading {

  implicit val timedIdReads: Reads[TimedId] =
    ((JsPath \ "time").read[Long] and (JsPath \ "rand").read[Int])(TimedId(_, _))

  implicit val messageKindReads = new Reads[Message.Kind] {
    override def reads(json: JsValue): JsResult[Kind] = json match {
      case jsString: JsString => MaybeToJsResult(Message.Kind(jsString.value))
      case _ => JsError("Expected JSON String, but found " + json)
    }
  }

  implicit val pingMessageReads: Reads[PingMessage] = (JsPath \ "id").read[TimedId].map(PingMessage(_))

  implicit val echoMessageReads: Reads[EchoMessage] =
    ((JsPath \ "id").read[TimedId] and (JsPath \ "inResponseToId").read[TimedId])(EchoMessage(_, _))

  implicit val inputMessageReads: Reads[InputMessage] =
    ((JsPath \ "id").read[TimedId] and (JsPath \ "input").read[String] and (JsPath\"num").read[Int])(InputMessage(_, _, _))

  implicit val messageReads = new Reads[Message] {
    override def reads(json: JsValue): JsResult[Message] = {
      (json \ "kind").validate[Message.Kind] match {
        case jsSuccess: JsSuccess[Message.Kind] =>
          val kind = jsSuccess.get
          kind match {
            case Message.Kind.ping => json.validate[PingMessage]
            case Message.Kind.echo => json.validate[EchoMessage]
            case Message.Kind.`input` => json.validate[InputMessage]
            case _ => JsError("Unknown message type '" + kind + "'")
          }
        case error: JsError => error
      }
    }
  }

}
