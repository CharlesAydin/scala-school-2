package com.hopper.scala.school.geocode

import com.twitter.server.TwitterServer
import google.GoogleGeocodeFilter
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.{Service, Http, Thrift}
import java.net.InetSocketAddress
import com.twitter.util.Await
import org.jboss.netty.handler.codec.http.{HttpResponse, HttpRequest}

/**
 * A simple finagle server that launches the cache service on the specified port.
 */
object GeocodeServer extends TwitterServer {

  val service =
    new GeocodeHttpService() andThen
    new GeocodingCitySearch(
      new GoogleGeocodeFilter() andThen
        Http.newClient("maps.googleapis.com:80").toService
    )



  val binding = flag("bind", new InetSocketAddress(9091), "The address to bind the thrift service to")

  def main() {

    val server = Http.serve(binding(), service)

    onExit {
      server.close()
    }

    Await.ready(server)
  }

  // Overriding this so it doesn't conflict with the cache server
  override val defaultHttpPort = 8081
}
