package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/11/2015.
 */
object VariStats {
  def apply(vari: Variation, ns: Map[Int, Int]): VariStats =
    VariStats(vari, ns.getOrElse(0, 0), ns.getOrElse(1, 0), ns.getOrElse(2, 0))
}

case class VariStats(vari: Variation, n0: Int, n1: Int, n2: Int) {

  def n(zygosity: Int): Int = zygosity match {
    case 0 => n0
    case 1 => n1
    case 2 => n2
    case _ => 0
  }

  def total = n0 + n1 + n2

  def maxZygosity = vari.chr match {
    case Chrs.chrY => 1
    case _ => 2
  }

  def freq(zygosity: Int) = n(zygosity).toDouble / total

  def report: String = "" + vari + (0 to maxZygosity).map(z => "" + z + ":" + n(z)).mkString("(", ", ", ")")

}
