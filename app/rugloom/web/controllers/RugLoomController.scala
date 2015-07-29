package rugloom.web.controllers

import play.api.mvc.{Action, Controller}
import rugloom.views.html.RugLoomView

/**
 * Created by oliverr on 7/29/2015.
 */
object RugLoomController extends Controller {

  def index = Action {
    Ok(RugLoomView.index("RugLoom"))
  }

}
