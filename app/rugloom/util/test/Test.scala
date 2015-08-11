package rugloom.util.test

/**
 * RugLoom - Explorative analysis pipeline prototype
 * Created by oliverr on 8/11/2015.
 */
object Test {

  implicit class AsTest[T](f: T => Boolean) extends Test[T] {
    override def apply(item: T): Boolean = f(item)

    override def toString = f.toString
  }

  implicit class EqualTest[T](item: T) extends Test[T] {
    override def apply(oi: T): Boolean = item == oi

    override def toString = item.toString
  }

}

trait Test[T] extends (T => Boolean) {

  def ||(test: Test[T]) = test match {
    case test: OrTest[T] => OrTest(this +: test.tests)
    case _ => OrTest(Seq(this, test))
  }

  def &&(test: Test[T]) = test match {
    case test: AndTest[T] => AndTest(this +: test.tests)
    case _ => AndTest(Seq(this, test))
  }

  def unary_! : Test[T] = NotTest(this)
}

case class OrTest[T](tests: Seq[Test[T]]) extends Test[T] {
  override def apply(item: T): Boolean = tests.foldLeft(false)(_ || _.apply(item))

  override def ||(test: Test[T]) = test match {
    case test: OrTest[T] => OrTest(tests ++ test.tests)
    case _ => OrTest(tests :+ test)
  }

  override def toString = tests.mkString("(", " || ", ")")
}

case class AndTest[T](tests: Seq[Test[T]]) extends Test[T] {
  override def apply(item: T): Boolean = tests.foldLeft(true)(_ && _.apply(item))

  override def &&(test: Test[T]) = test match {
    case test: AndTest[T] => AndTest(tests ++ test.tests)
    case _ => AndTest(tests :+ test)
  }

  override def toString = tests.mkString("(", " && ", ")")
}

case class NotTest[T](test: Test[T]) extends Test[T] {
  override def apply(item: T) = !test(item)

  override def unary_! = test

  override def toString = "!(" + test + ")"
}