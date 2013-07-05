package com.hopper.scala.school.geocode

import com.twitter.app.{App => TwitterApp}
import java.net.{SocketAddress, InetSocketAddress}
import com.twitter.finagle.{Group, Thrift}
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * A simple CLI client for the cache server.
 *
 * Note the use of pattern matching for both argument parsing and response handling.
 */
object GeocodeClient extends TwitterApp {

  val serviceBinding = flag("service", new InetSocketAddress(9090), "The address of the cache thrift server")

  def main() {

    val address: SocketAddress = serviceBinding()

  }
}
