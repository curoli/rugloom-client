package rugloom.shell

import java.io.File
import javax.script.ScriptEngineManager
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/31/2015.
 */
class RugLoomShell {

  val code = """println("Hi");""";

  val settings = new Settings
  val bootPath = (new File(System.getProperty("boot.class.path"))).toURI
  val javaPath = (new File(System.getProperty("java.class.path"))).toURI
  val compilerPath = java.lang.Class.forName("scala.tools.nsc.interpreter.IMain").getProtectionDomain.getCodeSource.getLocation
  val libPath = java.lang.Class.forName("scala.Some").getProtectionDomain.getCodeSource.getLocation

  println("bootPath=" + bootPath);
  println("javaPath=" + javaPath);
  println("compilerPath=" + compilerPath);
  println("libPath=" + libPath);

  settings.classpath.value = List(bootPath, javaPath, compilerPath, libPath) mkString java.io.File.pathSeparator
//  settings.classpath.value =
//    "C:\\Program Files (x86)\\sbt\\bin\\sbt-launch.jar;C:\\Users\\oliverr\\.ivy2\\cache\\org.scala-lang\\scala-compiler\\jars\\scala-compiler-2.11.7.jar;C:\\Users\\oliverr\\.ivy2\\cache\\org.scala-lang\\scala-library\\jars\\scala-library-2.11.7.jar"
  println(settings.classpath)
//  settings.usejavacp.value = true
//  settings.embeddedDefaults[RugLoomShell]
  val imain = new IMain(settings)

  println(imain)

  def lineEntered(line: String): Unit = imain.interpret(line)

}
