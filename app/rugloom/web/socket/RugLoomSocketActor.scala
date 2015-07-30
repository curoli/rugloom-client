package rugloom.web.socket

/**
 * Created by oliverr on 7/29/2015.
 * Actor to handle websocket communication
 */

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import rugloom.web.socket.MessageJsonReading.messageReads
import rugloom.web.socket.MessageJsonWriting.{echoMessageWrites, pingMessageWrites}

object RugLoomSocketActor {
  def props(out: ActorRef) = Props(new RugLoomSocketActor(out))
}

class RugLoomSocketActor(out: ActorRef) extends Actor {

  override def preStart(): Unit = {
    out ! Json.toJson(PingMessage.create)
  }

  def receive = {
    case json: JsValue =>
      json.validate[Message] match {
        case jsSuccess: JsSuccess[Message] =>
          val message = jsSuccess.get
          message match {
            case pingMessage: PingMessage =>
              println("I received a ping message: " + pingMessage)
              println("I'm going to respond with echo.")
              out ! Json.toJson(EchoMessage.create(pingMessage))
            case echoMessage: EchoMessage =>
              println("I have received an echo message: " + echoMessage)
            case _ =>
              println("I have received an unknown type of message: " + message)
          }
        case jsError: JsError =>
          println("There was a JsError: " + jsError)
      }
    case msg: String =>
      println("I received a String, which should not happen.")
  }
}