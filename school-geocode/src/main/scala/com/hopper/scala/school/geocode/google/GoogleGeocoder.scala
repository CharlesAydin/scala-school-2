package com.hopper.scala.school.geocode.google

import org.jboss.netty.handler.codec.http.{HttpResponse, HttpRequest}
import com.twitter.finagle.{Service, Filter}
import com.twitter.util.Future
import com.twitter.finagle.http.{Response, RequestBuilder}
import spray.json._
import com.hopper.scala.school.geocode.DataModel._
import java.net.URLEncoder

class GoogleGeocodeFilter extends Filter[String, GeocodeResponse, HttpRequest, HttpResponse] {

  def apply(request: String, service: Service[HttpRequest, HttpResponse]): Future[GeocodeResponse] = {

    val getRequest = RequestBuilder()
      .url(uriFor(request))
      .buildGet()

    service(getRequest) map { resp =>
      val jsonString = Response(resp).contentString
      jsonString.asJson.convertTo[GeocodeResponse]
    }
  }

  def uriFor(query: String): String =
    "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=" + URLEncoder.encode(query, "UTF-8")
}
