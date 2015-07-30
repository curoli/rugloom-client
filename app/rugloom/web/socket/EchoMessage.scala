package rugloom.web.socket

import rugloom.web.socket.Message.{Kind, Response}

/**
 * Created by oliverr on 7/29/2015.
 * A message in response to a ping message
 */
object EchoMessage {
  def create(pingMessage: PingMessage): EchoMessage = EchoMessage(TimedId.create, pingMessage.id)
}

case class EchoMessage(id: TimedId, inResponseToId: TimedId) extends Response {

  override def inResponseToKind: Kind = Message.Kind.ping

  override def kind: Kind = Message.Kind.echo
}
