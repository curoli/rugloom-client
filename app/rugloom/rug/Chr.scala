package rugloom.rug


/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/10/2015.
 */
object Chr {
  def apply(num: Int): Chr = Autosome(num)

  def apply(name: String): Chr = Allosome(name)

  val chr1 = Chr(1)
  val chr2 = Chr(2)
  val chr3 = Chr(3)
  val chr4 = Chr(4)
  val chr5 = Chr(5)
  val chr6 = Chr(6)
  val chr7 = Chr(7)
  val chr8 = Chr(8)
  val chr9 = Chr(9)
  val chr10 = Chr(10)
  val chr11 = Chr(11)
  val chr12 = Chr(12)
  val chr13 = Chr(13)
  val chr14 = Chr(14)
  val chr15 = Chr(15)
  val chr16 = Chr(16)
  val chr17 = Chr(17)
  val chr18 = Chr(18)
  val chr19 = Chr(19)
  val chr20 = Chr(20)
  val chr21 = Chr(21)
  val chr22 = Chr(22)
  val chrX = Chr("X")
  val chrY = Chr("Y")
}

sealed trait Chr {
  def toString: String
}

case class Autosome(num: Int) extends Chr {
  override def toString = "" + num
}

case class Allosome(name: String) extends Chr {
  override def toString = "" + name
}

