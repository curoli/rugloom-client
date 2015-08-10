package rugloom.rug

import rugloom.rug.GenoPos.Allosome

import scala.util.Random

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
class MockRug(nSamples: Int, nVaris: Int, sampleIdBase: String = "patient") extends Rug {

  val sampleIds = (0 until nSamples).map(sampleIdBase + _).map(SampleId(_)).toSet
  val random = new Random
  val males = sampleIds.filter(s => random.nextBoolean);

  def createVari: Variation = {
    val iChr = random.nextInt(23) + 1
    val chr = if (iChr < 23) {
      GenoPos.Autosome(iChr)
    } else {
      val chanceOfOnX = 0.75 // Roughly, if X and Y were equal in size. Of course, Y is shorter than X.
      if (random.nextDouble < chanceOfOnX) {
        GenoPos.chrX
      } else {
        GenoPos.chrY
      }
    }
    val pos = random.nextInt(65000000) // Approximate range, if all chroms were equal in size
    val genePos = GenoPos(chr, pos)
    val ref = Variation.baseSeq(random.nextInt(Variation.baseSeq.size))
    val seq = Variation.baseSeq(random.nextInt(Variation.baseSeq.size))
    Variation(genePos, ref, seq)
  }

  val varis = Iterator.fill(nVaris)(createVari).toSet

  val sampleVaris = varis.flatMap({ vari =>
    val freq = random.nextDouble * random.nextDouble * random.nextDouble
    sampleIds.map({ sampleId =>
      val isMale = males.contains(sampleId)
      val zygosity = if (isMale && vari.pos.chr.isInstanceOf[Allosome]) {
        if (random.nextDouble() < freq) 1 else 0
      } else if (!isMale && vari.pos.chr == GenoPos.chrY) {
        0
      } else {
        (if (random.nextDouble() < freq) 1 else 0) + (if (random.nextDouble() < freq) 1 else 0)
      }
      (sampleId, vari) -> Genotype(vari, zygosity)
    })
  }).toMap

}
