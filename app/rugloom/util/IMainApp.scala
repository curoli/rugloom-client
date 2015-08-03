package rugloom.util

import javax.script.ScriptEngineManager
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 7/31/2015.
 */
object IMainApp extends App {

  trait Whatever

  val settings = new Settings()
  settings.embeddedDefaults[Whatever]
  val imain = new IMain(settings)

  imain.interpret("2+3")

}
