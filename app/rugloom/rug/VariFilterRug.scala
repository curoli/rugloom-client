package rugloom.rug

import rugloom.rug.Pipe.IteratorFilterPipe
import rugloom.rug.VariFilterRug.{NVarisPipe, VarisPipe}

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/10/2015.
 */
object VariFilterRug {

  trait Wrapper extends Rug {
    def filterVaris(filter: Variation => Boolean): Rug = new VariFilterRug(this, filter)
  }

  class NVarisPipe(rug: VariFilterRug) extends Pipe[Int] {
    override def ! : Int = rug.rug.varis.!.count(rug.filter)
  }

  class VarisPipe(rug: VariFilterRug) extends Pipe[Iterator[Variation]] {
    override def ! : Iterator[Variation] = rug.rug.varis.!.filter(rug.filter)
  }

}

class VariFilterRug(val rug: Rug, val filter: Variation => Boolean) extends Rug with VariFilterRug.Wrapper {

  override def nSamps: Pipe[Int] = rug.nSamps

  override def nVaris: Pipe[Int] = new NVarisPipe(this)

  override def genotype(samp: String, vari: Variation): Pipe[Option[Genotype]] =
    if (filter(vari)) rug.genotype(samp, vari) else Pipe.NonePipe

  override def varis: Pipe[Iterator[Variation]] = new VarisPipe(this)

  override def sampGenos(samp: String): Pipe[Iterator[Genotype]] =
    new IteratorFilterPipe[Genotype](rug.sampGenos(samp), gt => filter(gt.variation))

  override def samps: Pipe[Iterator[String]] = rug.samps

  override def variGenos(vari: Variation): Pipe[Iterator[(String, Genotype)]] =
    if (filter(vari)) rug.variGenos(vari) else Pipe.EmptyPipe
}
