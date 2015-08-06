package rugloom.web.socket

import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/31/2015.
 */
case class InputMessage(id: TimedId, input: String, num: Int) extends Message {
  override def kind: Kind = Kind.input
}
