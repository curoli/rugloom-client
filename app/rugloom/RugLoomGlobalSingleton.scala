package rugloom

import java.io.{PrintStream, ByteArrayOutputStream}

import rugloom.util.MulticastOutputStream

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/5/2015.
 */
object RugLoomGlobalSingleton {

//  def init = println("Initializing the RugLoom global singleton.")
//
//  val oldOut = System.out
//  val oldErr = System.err
//  val forkedOut = new ByteArrayOutputStream
//  val forkedErr = new ByteArrayOutputStream
//
//  System.setOut(new PrintStream(new MulticastOutputStream(Seq(System.out, forkedOut))))
//  System.setErr(new PrintStream(new MulticastOutputStream(Seq(System.err, forkedErr))))
//
//  private def harvestForkedStream(os:ByteArrayOutputStream) : String = {
//    val content = os.toString
//    os.reset
//    content
//  }
//
//  def harvestOut : String = harvestForkedStream(forkedOut)
//  def harvestErr : String = harvestForkedStream(forkedErr)
//
}
