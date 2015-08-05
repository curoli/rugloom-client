package rugloom.util

import java.io.{FilterOutputStream, OutputStream}

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/5/2015.
 */
class MulticastOutputStream(val outs: Seq[OutputStream]) extends OutputStream {
  override def write(b: Int): Unit = outs.map(_.write(b))
}
