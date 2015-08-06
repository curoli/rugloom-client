package rugloom.util

import java.io.PrintStream

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/6/2015.
 */
class ChoppingPrintStream(listener: ChopListener) extends PrintStream(new ChoppingOutputStream(listener), true){

}
