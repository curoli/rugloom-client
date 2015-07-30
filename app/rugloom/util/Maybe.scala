package rugloom.util

/**
 * RugLoom - Explorative analysis prototype
 * Created by oliverr on 7/30/2015.
 */
sealed trait Maybe[+T] {

}

case class Yes[+T](value: T) extends Maybe[T] {

}

object No {
  def apply(msg: String): No = No(Seq(Snag(msg)))
}

case class No(snags: Seq[Snag]) extends Maybe[Nothing] {

}