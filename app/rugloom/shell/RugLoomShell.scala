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
  //  val javaPath = (new File(System.getProperty("java.class.path"))).toURI
  //  val compilerPath = java.lang.Class.forName("scala.tools.nsc.interpreter.IMain").getProtectionDomain.getCodeSource.getLocation
  //  val libPath = java.lang.Class.forName("scala.Some").getProtectionDomain.getCodeSource.getLocation
  val compilerPath = current.classloader.loadClass("scala.tools.nsc.interpreter.IMain").getProtectionDomain.getCodeSource.getLocation
  val libPath = current.classloader.loadClass("scala.Some").getProtectionDomain.getCodeSource.getLocation

  //  println("javaPath=" + javaPath);
  println("compilerPath=" + compilerPath);
  println("libPath=" + libPath);

  //  settings.classpath.value = List(javaPath, compilerPath, libPath) mkString java.io.File.pathSeparator
  //  settings.classpath.value =
  //    "C:\\Program Files (x86)\\sbt\\bin\\sbt-launch.jar;C:\\Users\\oliverr\\.ivy2\\cache\\org.scala-lang\\scala-compiler\\jars\\scala-compiler-2.11.7.jar;C:\\Users\\oliverr\\.ivy2\\cache\\org.scala-lang\\scala-library\\jars\\scala-library-2.11.7.jar"
  println("classpath: " + settings.classpath)
  settings.usejavacp.value = true
  println("classpath: " + settings.classpath)
  settings.embeddedDefaults[RugLoomShell]
  println("classpath: " + settings.classpath)
  val imain = new IMain(settings) {
    override def parentClassLoader: ClassLoader = current.classloader
  }
  imain.addUrlsToClassPath(compilerPath, libPath)

  println("IMain class loader: " + imain.classLoader)
  println("IMain compiler classpath: " + imain.compilerClasspath)

  def lineEntered(line: String): Unit = imain.interpret(line)

}
