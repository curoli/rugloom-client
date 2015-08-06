package rugloom.web.socket

/**
 * Created by oliverr on 7/29/2015.
 * Actor to handle websocket communication
 */

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import rugloom.shell.ShellOutput.Listener
import rugloom.shell.{RugLoomShell, ShellOutput}
import rugloom.web.socket.MessageJsonReading.messageReads
import rugloom.web.socket.MessageJsonWriting.{echoMessageWrites, pingMessageWrites, shellOutputMessageWrites}

object RugLoomSocketActor {
  def props(out: ActorRef) = Props(new RugLoomSocketActor(out))
}

class RugLoomSocketActor(out: ActorRef) extends Actor {

  val shellListener = new Listener {
    override def output(response: ShellOutput): Unit = self ! response
  }

  val shell = new RugLoomShell(shellListener)
  var lineEnteredMessages: Map[Int, InputMessage] = Map.empty

  override def preStart(): Unit = {
    out ! Json.toJson(PingMessage.create)
  }

  def receive = {
    case json: JsValue =>
      json.validate[Message] match {
        case jsSuccess: JsSuccess[Message] =>
          val message = jsSuccess.get
          message match {
            case message: PingMessage => out ! Json.toJson(EchoMessage.create(message))
            case message: EchoMessage => {}
            case message: InputMessage =>
              lineEnteredMessages += message.num -> message
              shell.lineEntered(message.num, message.input)
            case _ =>
              println("I have received an unknown type of message: " + message)
          }
        case jsError: JsError =>
          println("There was a JsError: " + jsError)
      }
    case shellOutput: ShellOutput =>
      out ! Json.toJson(ShellOutputMessage.create(shellOutput))
    case msg: String =>
      println("I received a String, which should not happen.")
  }
}