package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
case class NamedRug(name: String) extends Rug {
  override def toString: String = "Rug(" + name + ")"
}

