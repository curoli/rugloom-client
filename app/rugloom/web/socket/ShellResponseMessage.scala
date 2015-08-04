package rugloom.web.socket

import rugloom.shell.ShellResponse
import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/4/2015.
 */
object ShellResponseMessage {

  def create(lineEnteredMessage: LineEnteredMessage, response: ShellResponse): ShellResponseMessage =
    ShellResponseMessage(TimedId.create, lineEnteredMessage.id, response)

}

case class ShellResponseMessage(id: TimedId, inResponseToId: TimedId, response: ShellResponse) extends Message.Response {

  override def inResponseToKind: Kind = Message.Kind.lineEntered

  override def kind: Kind = Message.Kind.shellResponse


}
