package rugloom.shell

import java.io.File
import javax.script.ScriptEngineManager
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import play.api.Play.current

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/31/2015.
 */
class RugLoomShell {

  val settings = new Settings
  val sbtClasspath = System.getProperty("rugloom.interpreter.classpath")
  println("rugloom.interpreter.classpath = " + sbtClasspath)
  settings.classpath.value = "." + File.pathSeparator + sbtClasspath
  val imain = new IMain(settings) {
    override def parentClassLoader: ClassLoader = current.classloader
  }

  def lineEntered(line: String): Unit = imain.interpret(line)

}
