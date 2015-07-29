package rugloom.web.controllers

import play.api.mvc.{WebSocket, Action, Controller}
import rugloom.views.html.RugLoomView
import play.api.Play.current

/**
 * Created by oliverr on 7/29/2015.
 * Controller for RugLoom webservices
 */
object RugLoomController extends Controller {

  def index = Action {
    Ok(RugLoomView.index("RugLoom"))
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    RugLoomSocketActor.props(out)
  }

}