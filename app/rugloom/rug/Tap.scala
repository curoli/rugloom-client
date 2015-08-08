package rugloom.rug

import scala.concurrent.Future

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
trait Tap[T] {

  def ? : Future[T]
  def ! : T

}