package rugloom.shell

import java.io.{PrintWriter, StringWriter, File}

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.Results.Result
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
  val stringWriter = new StringWriter()
  val printWriter = new PrintWriter(stringWriter)
  val imain = new IMain(settings, printWriter)

  def safeCall(request: imain.Request, name: String): AnyRef = {
    try {
      request.lineRep.call(name)
    }
    catch {
      case e: Exception => e
    }
  }

  def safePrintln(request: imain.Request, name: String): Unit = {
    val x = safeCall(request, name)
    val clazz = if (x != null) x.getClass else null
    println(name + " = " + x + " (" + clazz + ")")
  }

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
      response = response.withConsoleOut(buffer.toString)
      buffer.setLength(0)
      listener.responseReceived(response)
    })
  }

}
