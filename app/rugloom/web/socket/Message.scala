package rugloom.web.socket

import rugloom.web.socket.Message.Kind

/**
 * Created by oliverr on 7/29/2015.
 * Messages for front- and backend to communicate
 */
object Message {

  sealed trait Kind

  case object ping extends Kind

  case object echo extends Kind

  trait Response extends Message {
    def inResponseToId: TimedId

    def inResponseToKind: Kind
  }

}

trait Message {
  def id: TimedId

  def kind: Kind
}
