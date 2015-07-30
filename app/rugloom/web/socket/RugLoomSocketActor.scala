package rugloom.web.socket

/**
 * Created by oliverr on 7/29/2015.
 * Actor to handle websocket communication
 */

import akka.actor.{Actor, Props, ActorRef}
import play.api.libs.json.Json
import rugloom.web.socket.MessageJsonWriting.pingMessageWrites

object RugLoomSocketActor {
  def props(out: ActorRef) = Props(new RugLoomSocketActor(out))
}

class RugLoomSocketActor(out: ActorRef) extends Actor {

  override def preStart() : Unit = {
    out ! Json.toJson(PingMessage.create)
  }

  def receive = {
    case msg: String =>
      out ! Json.toJson(PingMessage.create)
  }
}