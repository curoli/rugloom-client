import play.api.{GlobalSettings, Application}

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
object Global extends GlobalSettings {

  override def onStart(app: Application): Unit = println("On Start!")

  override def onStop(app: Application): Unit = println("On Stop!")

}
