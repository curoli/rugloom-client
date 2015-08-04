package rugloom.shell

import scala.tools.nsc.interpreter.Results.Result

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/4/2015.
 */
object ShellResponse {

  trait Listener {
    def responseReceived(response: ShellResponse)
  }

  def apply(lineNumber: Int, lineEntered: String, resultReturned: Result): ShellResponse =
    ShellResponse(lineNumber, lineEntered, resultReturned, None, Seq.empty[String])

}

case class ShellResponse(num: Int, lineEntered: String, resultReturned: Result, resultLineOpt: Option[String],
                         linesConsole: Seq[String]) {

  def withResultLine(resultLine: String): ShellResponse = copy(resultLineOpt = Some(resultLine))

  def withOutLine(lineConsole: String): ShellResponse = copy(linesConsole = linesConsole :+ lineConsole)

}
