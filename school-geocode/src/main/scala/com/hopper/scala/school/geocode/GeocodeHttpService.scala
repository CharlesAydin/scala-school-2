package com.hopper.scala.school.geocode

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.{Filter, Service}
import com.twitter.util.{NonFatal, Future}
import com.twitter.finagle.http.{Response, Request}
import spray.json._
import com.hopper.scala.school.geocode.DataModel._

class GeocodeHttpService extends Filter[HttpRequest, HttpResponse, String, City] {

  def apply(request: HttpRequest, service: Service[String, City]): Future[HttpResponse] = {

    val req = Request(request)

    if (req.path == "/search") {

      req.params.get("q") map { queryString =>

        service(queryString) map { result =>
          val success = Response()
          success.contentString = result.toJson.prettyPrint
          success
        } rescue {
          case NonFatal(e) => error(e.getMessage)
        }
      } getOrElse {
        error("No query string for search")
      }
    } else {
      error("Only supports path `search`")
    }
  }

  def error(msg: String) = {
    val resp = Response(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST)

    resp.contentString = Map("error" -> msg).toJson.prettyPrint

    Future.value(resp)
  }
}
