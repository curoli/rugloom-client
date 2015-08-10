package rugloom.rug

import rugloom.rug.MockRug.{GenotypePipe, NSampsPipe, NVarisPipe, SampGenosPipe, SampsPipe, VariGenosPipe, VarisPipe}

import scala.util.Random

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
object MockRug {

  class NSampsPipe(rug: MockRug) extends Pipe[Int] {
    override def ! : Int = rug.sampleIds.size
  }

  class SampsPipe(rug: MockRug) extends Pipe[Iterator[String]] {
    override def ! : Iterator[String] = rug.sampleIds.iterator.map(_.id)
  }

  class NVarisPipe(rug: MockRug) extends Pipe[Int] {
    override def ! : Int = rug.vs.size
  }

  class VarisPipe(rug: MockRug) extends Pipe[Iterator[Variation]] {
    override def ! : Iterator[Variation] = rug.vs.iterator
  }

  class SampGenosPipe(rug: MockRug, samp: String) extends Pipe[Iterator[Genotype]] {
    override def ! : Iterator[Genotype] = {
      val sampId = SampleId(samp)
      rug.vs.iterator.flatMap(vari => rug.sampleVaris.get((sampId, vari)))
    }
  }

  class VariGenosPipe(rug: MockRug, vari: Variation) extends Pipe[Iterator[(String, Genotype)]] {
    override def ! : Iterator[(String, Genotype)] = rug.sampleVaris.iterator.collect({
      case ((SampleId(samp), v: Variation), genotype: Genotype) if (v == vari) => (samp, genotype)
    })
  }

  class GenotypePipe(rug: MockRug, samp: String, vari: Variation) extends Pipe[Option[Genotype]] {
    override def ! : Option[Genotype] = rug.sampleVaris.get((SampleId(samp), vari))
  }

}

class MockRug(nSs: Int, nVs: Int, sampleIdBase: String = "patient") extends Rug {

  val sampleIds = (0 until nSs).map(sampleIdBase + _).map(SampleId(_)).toSet
  val random = new Random
  val males = sampleIds.filter(s => random.nextBoolean);

  def createVari: Variation = {
    val iChr = random.nextInt(23) + 1
    val chr = if (iChr < 23) {
      Autosome(iChr)
    } else {
      val chanceOfOnX = 0.75 // Roughly, if X and Y were equal in size. Of course, Y is shorter than X.
      if (random.nextDouble < chanceOfOnX) {
        Chr.chrX
      } else {
        Chr.chrY
      }
    }
    val pos = random.nextInt(65000000) // Approximate range, if all chroms were equal in size
    val genePos = GenoPos(chr, pos)
    val ref = Variation.baseSeq(random.nextInt(Variation.baseSeq.size))
    val seq = {
      var seq: String = null
      while (seq == null || seq == ref) seq = Variation.baseSeq(random.nextInt(Variation.baseSeq.size))
      seq
    }
    Variation(genePos, ref, seq)
  }

  val vs = Iterator.fill(nVs)(createVari).toSet

  val sampleVaris = vs.flatMap({ vari =>
    val freq = random.nextDouble * random.nextDouble * random.nextDouble
    sampleIds.map({ sampleId =>
      val isMale = males.contains(sampleId)
      val zygosity = if (isMale && vari.pos.chr.isInstanceOf[Allosome]) {
        if (random.nextDouble() < freq) 1 else 0
      } else if (!isMale && vari.pos.chr == Chr.chrY) {
        0
      } else {
        (if (random.nextDouble() < freq) 1 else 0) + (if (random.nextDouble() < freq) 1 else 0)
      }
      (sampleId, vari) -> Genotype(vari, zygosity)
    })
  }).toMap

  override def nSamps: Pipe[Int] = new NSampsPipe(this)

  override def samps: Pipe[Iterator[String]] = new SampsPipe(this)

  override def nVaris: Pipe[Int] = new NVarisPipe(this)

  override def varis: Pipe[Iterator[Variation]] = new VarisPipe(this)

  override def sampGenos(samp: String): Pipe[Iterator[Genotype]] = new SampGenosPipe(this, samp)

  override def variGenos(vari: Variation): Pipe[Iterator[(String, Genotype)]] = new VariGenosPipe(this, vari)

  override def genotype(samp: String, vari: Variation): Pipe[Option[Genotype]] = new GenotypePipe(this, samp, vari)

}
