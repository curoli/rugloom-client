package rugloom.web.socket

import rugloom.util.{No, Yes, Maybe}
import rugloom.web.socket.Message.Kind

/**
 * Created by oliverr on 7/29/2015.
 * Messages for front- and backend to communicate
 */
object Message {

  sealed trait Kind

  object Kind {

    case object ping extends Kind

    case object echo extends Kind

    def apply(string: String): Maybe[Kind] = {
      string match {
        case "ping" => Yes(ping)
        case "echo" => Yes(echo)
        case _ => No("Unknown message kind '" + string + "'")
      }
    }

  }

  trait Response extends Message {
    def inResponseToId: TimedId

    def inResponseToKind: Kind
  }

}

trait Message {
  def id: TimedId

  def kind: Kind
}
