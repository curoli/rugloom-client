package rugloom.util

import play.api.libs.json.{JsResult, JsError, JsSuccess}

/**
 * RugLoom - Explorative analysis prototype
 * Created by oliverr on 7/30/2015.
 */
object MaybeToJsResult {

  def yesToJsSuccess[T](yes: Yes[T]): JsSuccess[T] = new JsSuccess[T](yes.value)

  def noToJsError(no: No): JsError = {
    val jsErrors = no.snags.map(_.msg).map(JsError(_))
    jsErrors.tail.foldLeft(jsErrors.head)(_ ++ _)
  }

  def apply[T](maybe: Maybe[T]): JsResult[T] = maybe match {
    case yes: Yes[T] => yesToJsSuccess(yes)
    case no: No => noToJsError(no)
  }

}
