package rugloom.web.socket

/**
 * Created by oliverr on 7/29/2015.
 * Actor to handle websocket communication
 */
import akka.actor._

object RugLoomSocketActor {
  def props(out: ActorRef) = Props(new RugLoomSocketActor(out))
}

class RugLoomSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}