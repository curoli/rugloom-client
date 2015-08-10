package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/7/2015.
 */
object GenoPos {

}

case class GenoPos(chr: Chr, pos: Int) {
  override def toString = chr + "_" + pos
}
