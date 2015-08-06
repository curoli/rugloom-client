package rugloom.rug

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
object Rug {

  def apply(name: String): Rug = NamedRug(name)
}

trait Rug {

}
