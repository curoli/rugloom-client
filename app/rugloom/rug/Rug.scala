package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
object Rug {

}

trait Rug {

  def nSamps: Pipe[Int]

  def samps: Pipe[Iterator[String]]

  def nVaris: Pipe[Int]

  def varis: Pipe[Iterator[Variation]]

}
