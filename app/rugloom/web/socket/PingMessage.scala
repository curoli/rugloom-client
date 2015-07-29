package rugloom.web.socket

import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis prototype
 * Created by oliverr on 7/29/2015.
 * A message to request an echo.
 */
object PingMessage {
  def create: PingMessage = PingMessage(TimedId.create)
}

case class PingMessage(id: TimedId) extends Message {
  override def kind: Kind = Message.ping
}
