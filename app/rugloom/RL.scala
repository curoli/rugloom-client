package rugloom

import rugloom.rug.{Rug, MockRug}

/**
 * RugLoom - A prototype for a pipeline building toolkit
 * Created by oruebenacker on 8/9/15.
 */
object RL {

  def mock(nSamples: Int, nVaris: Int, sampleIdBase: String = "patient"): Rug =
    new MockRug(nSamples, nVaris, sampleIdBase)

}
