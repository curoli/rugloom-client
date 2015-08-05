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

}

case class ShellResponse(num: Int, lineEntered: String, resultReturned: Result, reportOut: String = "") {

  def withReportOut(consoleOut: String): ShellResponse = copy(reportOut = consoleOut)


}
