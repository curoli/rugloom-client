package rugloom.shell

import scala.tools.nsc.interpreter.Results.Result

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/4/2015.
 */
object ShellResponse {

  sealed trait Kind
  case object ok extends Kind
  case object fail extends Kind
  case object out extends Kind
  case object err extends Kind

  trait Listener {
    def responseReceived(response: ShellResponse)
  }

}

case class ShellResponse(num: Int, lineEntered: String, resultReturned: Result, report: String = "",
                         out: String = "", err: String = "") {

  def withReport(report: String): ShellResponse = copy(report = report)

  def appendReport(reportText: String): ShellResponse = copy(report = report + reportText)

  def appendOut(outText: String): ShellResponse = copy(out = out + outText)

  def appendErr(errText: String): ShellResponse = copy(err = err + errText)


}
