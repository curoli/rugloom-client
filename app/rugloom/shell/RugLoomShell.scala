package rugloom.shell

import java.io.{File, PrintWriter, StringWriter}

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.util.{Failure, Success, Try}

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/31/2015.
 */
object RugLoomShell {
}

class RugLoomShell(val listener: ShellResponse.Listener) {


  val settings = new Settings
  val sbtClasspath = System.getProperty("rugloom.interpreter.classpath")
  //  println("rugloom.interpreter.classpath = " + sbtClasspath)
  settings.classpath.value = "." + File.pathSeparator + sbtClasspath
  settings.embeddedDefaults[RugLoomShell]
  val stringWriter = new StringWriter()
  val printWriter = new PrintWriter(stringWriter)
  val imain = new IMain(settings, printWriter)


  val greeting = "Hello, World!"

  val predef = new Predef

  imain.bind("predef", "rugloom.shell.Predef", predef)
  imain.interpret("import predef._")

  def lineEntered(num: Int, line: String): Unit = {
    synchronized({
      val result = imain.interpret(line)
      var response = ShellResponse(num, line, result)
      listener.responseReceived(response)
      val prevRequestList = imain.prevRequestList
      if (prevRequestList.nonEmpty) {
        val lastRequest = prevRequestList.last
        Try(lastRequest.lineRep.call("$result")) match {
          case Success(result) =>
            println("Result is: " + result)
          case Failure(ex) => {
            println("Exception thrown: " + ex)
          }
        }
      }
      val buffer: StringBuffer = stringWriter.getBuffer
      response = response.withReportOut(buffer.toString)
      buffer.setLength(0)
      listener.responseReceived(response)
    })
  }

}
