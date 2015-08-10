package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
object Pipe {

  object NonePipe extends Pipe[None.type] {
    override def ! : None.type = None
  }

  object EmptyPipe extends Pipe[Iterator[Nothing]] {
    override def ! : Iterator[Nothing] = Iterator.empty
  }

  class IteratorFilterPipe[T](pipe: Pipe[Iterator[T]], filter: T => Boolean) extends Pipe[Iterator[T]] {
    override def ! : Iterator[T] = pipe.!.filter(filter)
  }

}

trait Pipe[+T] {

  def ! : T

}
