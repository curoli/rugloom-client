package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
object Variation {
  val bases = Set("A", "T", "G", "C")
  val baseSeq = bases.toSeq

  def apply(chr: Int, pos: Int, ref: String, seq: String): Variation = Variation(Chr(chr), pos, ref, seq)

  def apply(chr: String, pos: Int, ref: String, seq: String): Variation = Variation(Chr(chr), pos, ref, seq)
}

case class Variation(chr: Chr, pos: Int, ref: String, seq: String) {
  override def toString = pos + "_" + ref + "_" + seq

  def isSnp: Boolean = ref.length == 1 && seq.length == 1

  def isDel: Boolean = seq.length == 0

  def isIns: Boolean = ref.length == 0

  def isSub: Boolean = ref.length > 0 && seq.length > 0
}
