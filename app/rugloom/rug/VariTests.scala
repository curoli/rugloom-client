package rugloom.rug

import rugloom.util.test.Test

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/11/2015.
 */
object VariTests {

  case class HasChr(chrTest: Test[Chr]) extends Test[Variation] {
    override def apply(vari: Variation): Boolean = chrTest(vari.chr)

    override def toString = "HasChr(" + chrTest + ")"
  }

  case class HasPos(posTest: Test[Int]) extends Test[Variation] {
    override def apply(vari: Variation): Boolean = posTest(vari.pos)

    override def toString = "HasPos(" + posTest + ")"
  }

  case class HasRef(refTest: Test[String]) extends Test[Variation] {
    override def apply(vari: Variation): Boolean = refTest(vari.ref)

    override def toString = "HasRef(" + refTest + ")"
  }

  case class HasSeq(seqTest: Test[String]) extends Test[Variation] {
    override def apply(vari: Variation): Boolean = seqTest(vari.seq)

    override def toString = "HasSeq(" + seqTest + ")"
  }

  case class InRange(min: Int, max: Int) extends Test[Variation] {
    override def apply(vari: Variation): Boolean = vari.pos >= min && vari.pos <= max

    override def toString = "InRange(" + min + "," + max + ")"
  }

}
