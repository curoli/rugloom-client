package rugloom.util

import java.io.{PrintStream, OutputStream}

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
object OutErrForker {

  val originalOut = System.out
  val originalErr = System.err

  val autoFlush = true;

  def forkOut(alternateOut: OutputStream): Unit =
    System.setOut(new PrintStream(new MulticastOutputStream(Seq(originalOut, alternateOut)), autoFlush))

  def forkErr(alternateErr: OutputStream): Unit =
    System.setOut(new PrintStream(new MulticastOutputStream(Seq(originalErr, alternateErr)), autoFlush))

  def restoreOriginalOut(): Unit = System.setOut(originalOut)

  def restoreOriginalErr(): Unit = System.setErr(originalErr)

}
