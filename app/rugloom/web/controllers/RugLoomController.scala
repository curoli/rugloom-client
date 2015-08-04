package rugloom.web.controllers

import play.api.Play.current
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller, WebSocket}
import rugloom.views.html.RugLoomView
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
    RugLoomSocketActor.props(out)
  }

}
