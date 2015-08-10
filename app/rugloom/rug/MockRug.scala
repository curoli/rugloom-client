package rugloom.rug

import rugloom.rug.GenoPos.Allosome

import scala.util.Random

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
class MockRug(nSamples: Int, nVaris: Int, sampleIdBase : String = "patient") extends Rug {

  var sampleIds = Set.empty[SampleId]
  var sampleGenotypes = Map.empty[SampleId, Set[Genotype]]
  var variSampleGenotypes = Map.empty[Variation, Set[(SampleId, Genotype)]]
  var males = Set.empty[SampleId]
  val random = new Random
  for (iSample <- 0 until nSamples) {
    val sampleId = SampleId(sampleIdBase + iSample)
    sampleIds += sampleId
    if (random.nextBoolean()) males += sampleId
  }

  var muts = Set.empty[Variation]
  for (iMut <- 0 until nVaris) {
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
    val vari = Variation(genePos, ref, seq)
    val freq = 0.5 * random.nextDouble()
    for (sampleId <- sampleIds) {
      val isMale = males.contains(sampleId)
      val zygosity = if (isMale && chr.isInstanceOf[Allosome]) {
        if (random.nextDouble() < freq) 1 else 0
      } else if (!isMale && chr == GenoPos.chrY) {
        0
      } else {
        (if (random.nextDouble() < freq) 1 else 0) + (if (random.nextDouble() < freq) 1 else 0)
      }
      if (zygosity > 0) {
        val genotype = Genotype(vari, zygosity)
        val genotypes = sampleGenotypes.getOrElse(sampleId, Set.empty) + genotype
        sampleGenotypes += sampleId -> genotypes
        val variGenotypes = variSampleGenotypes.getOrElse(vari, Set.empty) + ((sampleId, genotype))
        variSampleGenotypes += vari -> variGenotypes
      }
    }
  }

}
