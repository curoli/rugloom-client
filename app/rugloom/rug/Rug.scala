package rugloom.rug

import play.api.libs.functional.Variant
import rugloom.util.test.Test

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

  def sampGenos(samp: String): Pipe[Iterator[Genotype]]

  def variGenos(vari: Variation): Pipe[Iterator[(String, Genotype)]]

  def genotype(samp: String, vari: Variation): Pipe[Option[Genotype]]

  def filterVaris(filter: Test[Variation]): Rug

  def variStats: Pipe[Iterator[VariStats]]

}
