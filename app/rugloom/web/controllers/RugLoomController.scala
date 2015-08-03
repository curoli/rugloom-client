package rugloom.web.controllers

import play.api.libs.json.JsValue
import play.api.mvc.{WebSocket, Action, Controller}
import rugloom.shell.RugLoomShell
import rugloom.views.html.RugLoomView
import play.api.Play.current
import rugloom.web.socket.RugLoomSocketActor

/**
 * Created by oliverr on 7/29/2015.
 * Controller for RugLoom webservices
 */
object RugLoomController extends Controller {

  def index = Action {
    Ok(RugLoomView.index("RugLoom"))
  }

  def socket = WebSocket.acceptWithActor[JsValue, JsValue] { request => out =>
    RugLoomSocketActor.props(out, new RugLoomShell)
  }

}
