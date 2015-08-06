package rugloom.web.socket

import rugloom.shell.ShellOutput
import rugloom.web.socket.Message.Kind

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/4/2015.
 */
object ShellOutputMessage {

  def create(output: ShellOutput): ShellOutputMessage = ShellOutputMessage(TimedId.create, output)

}

case class ShellOutputMessage(id: TimedId, output: ShellOutput) extends Message {

  override def kind: Kind = Message.Kind.output

}
