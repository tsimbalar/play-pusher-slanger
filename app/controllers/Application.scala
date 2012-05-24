package controllers

import play.api._
import libs.json.{JsObject, JsString, JsNumber}
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import utils.{PusherClientConfig, Pusher}

object Application extends Controller {
  val defaultChannel = "default-channel"
  val defaultEvent = "default-event"

  def index = Action {
    Ok(views.html.index(PusherClientConfig.default, defaultChannel, defaultEvent))
  }
  val messageForm = Form("msg" -> text)

  def postMessage = Action { implicit request =>
    Async {
      val msg = messageForm.bindFromRequest.get
      Pusher.trigger(defaultChannel,defaultEvent,JsObject(Seq("message" -> JsString(msg))).toString()).map { response =>
        Ok("triggered with : " + msg)
      }
    }
  }
}