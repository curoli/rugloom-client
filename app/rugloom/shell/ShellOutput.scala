package rugloom.shell

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/4/2015.
 */
object ShellOutput {

  case class Ok(text: String) extends ShellOutput {
    override def kindAsString = "ok"
  }

  case class Fail(text: String) extends ShellOutput {
    override def kindAsString = "fail"
  }

  case class Out(text: String) extends ShellOutput {
    override def kindAsString = "out"
  }

  case class Err(text: String) extends ShellOutput {
    override def kindAsString = "err"
  }

  trait Listener {
    def output(output: ShellOutput)

    def ok(text: String) = output(Ok(text))

    def fail(text: String) = output(Fail(text))

    def out(text: String) = output(Out(text))

    def err(text: String) = output(Err(text))
  }

}

trait ShellOutput {
  def kindAsString: String

  def text: String
}
