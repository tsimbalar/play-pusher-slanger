package utils

import java.util.Date
import play.api.libs.ws.WS
import play.api.Play
import java.net.URLEncoder
import play.api.libs.ws
import play.api.libs.concurrent.Promise
import play.api.Play.current

// Implementing the publishing Pusher REST API
// @see http://pusher.com/docs/rest_api
// with Play (scala) Framework
// @see http://scala.playframework.org/

class Pusher(host:String, port:Int, appId:String, key:String, secret:String) {

  import java.security.MessageDigest
  import java.math.BigInteger
  import javax.crypto.Mac
  import javax.crypto.spec.SecretKeySpec

  def trigger(channel: String, event: String, message: String): Promise[ws.Response] = {
    val domain = host + ":" + port
    val url = "/apps/" + appId + "/channels/" + channel + "/events";
    val body = message

    val params = List(
      ("auth_key", key),
      ("auth_timestamp", (new Date().getTime() / 1000) toInt),
      ("auth_version", "1.0"),
      ("name", event),
      ("body_md5", md5(body))
    ).sort((a, b) => a._1 < b._1).map(o => o._1 + "=" + URLEncoder.encode(o._2.toString)).mkString("&");

    val signature = sha256(List("POST", url, params).mkString("\n"), secret);

    WS.url("http://" + domain + url + "?" + params + "&auth_signature=" + URLEncoder.encode(signature)).post(body)
  }

  def byteArrayToString(data: Array[Byte]) = {
    val hash = new BigInteger(1, data).toString(16);
    "0" * (32 - hash.length) + hash
  }

  def md5(s: String): String = byteArrayToString(MessageDigest.getInstance("MD5").digest(s.getBytes("US-ASCII")));

  def sha256(s: String, secret: String): String = {
    val mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
    val digest = mac.doFinal(s.getBytes());
    String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
  }
}

object Pusher extends Pusher(
  host = Play.configuration.getString("pusher.server.host").getOrElse("api.pusherapp.com"),
  port = Play.configuration.getInt("pusher.server.port").getOrElse(80),
  appId = Play.configuration.getString("pusher.appId").get,
  key = Play.configuration.getString("pusher.key").get,
  secret = Play.configuration.getString("pusher.secret").get){
}

// port for ws secure/not secure
class PusherClientConfig(val key:String, val host:String, val wsPort:Int, val wssPort:Int)

object PusherClientConfig{
  def default:PusherClientConfig = {
    new PusherClientConfig(
      host = Play.configuration.getString("pusher.client.host").getOrElse("api.pusherapp.com"),
      wsPort = Play.configuration.getInt("pusher.client.port").getOrElse(80),
      wssPort = Play.configuration.getInt("pusher.client.securePort").getOrElse(80),
      key=Play.configuration.getString("pusher.key").get
      )
  }
}

/*
Usage example :

object WebSockets extends Pusher {
  val channel = Play.configuration.getProperty("websockets.channel")
  def trigger(event:String, message:String):HttpResponse = trigger(channel, event, message)
}

object Test {
  def test = WebSockets.trigger("hello", "{ \"message\": \"test\" }")
}
*/