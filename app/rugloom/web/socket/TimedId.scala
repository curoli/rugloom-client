package rugloom.web.socket

import scala.util.Random

/**
 * RugLoom - Explorative analysis prototype
 * Created by oliverr on 7/29/2015.
 * A unique id, based on system time and a random value.
 */
object TimedId {
  val random = new Random()

  def create: TimedId = TimedId(System.currentTimeMillis(), random.nextInt())
}

case class TimedId(time: Long, rand: Int) {

}
