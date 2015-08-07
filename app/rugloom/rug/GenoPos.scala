package rugloom.rug

import rugloom.rug.GenoPos.Chr

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
object GenoPos {

  sealed trait Chr {
    def toString: String
  }

  case class Autosome(num: Int) extends Chr {
    override def toString = "" + num
  }

  sealed trait Allosome extends Chr

  object chrX extends Allosome {
    override def toString = "X"
  }

  object chrY extends Allosome {
    override def toString = "Y"
  }

}

case class GenoPos(chr: Chr, pos: Int) {
  override def toString = chr + "_" + pos
}
