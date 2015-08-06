package rugloom.util

import java.io.ByteArrayOutputStream

import rugloom.util.ChoppingOutputStream.ChopListener

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
object ChoppingOutputStream {

  trait ChopListener {
    def nextChop(chop: String)
  }

}

class ChoppingOutputStream(listener: ChopListener) extends ByteArrayOutputStream {

  override def flush: Unit = {
    synchronized({
      listener.nextChop(toString)
      reset
    })
  }

}
