package rugloom.web.socket

/**
 * Created by oliverr on 7/29/2015.
 * Actor to handle websocket communication
 */

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import rugloom.shell.ShellResponse.Listener
import rugloom.shell.{ShellResponse, RugLoomShell}
import rugloom.web.socket.MessageJsonReading.messageReads
import rugloom.web.socket.MessageJsonWriting.{echoMessageWrites, pingMessageWrites, shellResponseMessageWrites}

object RugLoomSocketActor {
  def props(out: ActorRef) = Props(new RugLoomSocketActor(out))
}

class RugLoomSocketActor(out: ActorRef) extends Actor {

  val shellListener = new Listener {
    override def responseReceived(response: ShellResponse): Unit = {
      self ! response
      println(response)
    }
  }

  val shell = new RugLoomShell(shellListener)
  var lineEnteredMessages: Map[Int, LineEnteredMessage] = Map.empty

  override def preStart(): Unit = {
    out ! Json.toJson(PingMessage.create)
  }

  def receive = {
    case json: JsValue =>
      json.validate[Message] match {
        case jsSuccess: JsSuccess[Message] =>
          val message = jsSuccess.get
          message match {
            case message: PingMessage =>
              println("I received a ping message: " + message)
              println("I'm going to respond with echo.")
              out ! Json.toJson(EchoMessage.create(message))
            case message: EchoMessage =>
              println("I have received an echo message: " + message)
            case message: LineEnteredMessage =>
              lineEnteredMessages += message.num -> message
              shell.lineEntered(message.num, message.line)
            case _ =>
              println("I have received an unknown type of message: " + message)
          }
        case jsError: JsError =>
          println("There was a JsError: " + jsError)
      }
    case shellResponse: ShellResponse =>
      lineEnteredMessages.get(shellResponse.num) match {
        case Some(lineEnteredMessage) =>
          out ! Json.toJson(ShellResponseMessage.create(lineEnteredMessage, shellResponse))
        case None => println("Strange: received a shell response, but can't find lineEnteredMessage")
      }
    case msg: String =>
      println("I received a String, which should not happen.")
  }
}