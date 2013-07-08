package school.cache

import com.twitter.app.{App => TwitterApp}
import java.net.{SocketAddress, InetSocketAddress}
import com.twitter.finagle.{Group, Thrift}
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * A simple CLI client for the cache server.
 *
 * Note the use of pattern matching for both argument parsing and response handling.
 */
object CacheClient extends TwitterApp {

  val serviceBinding = flag("service", new InetSocketAddress(9090), "The address of the cache thrift server")

  def main() {

    val address: SocketAddress = serviceBinding()

    val client = new Cache.FinagledClient(
      Thrift.newClient(Group(address)).toService,
      new TBinaryProtocol.Factory()
    )

    args.toList match {
      case "put" :: key :: value :: _ => {
        client.put(key, value)()
        println(s"Put value [$value] at key [$key]")
      }
      case "get" :: key :: _ => {
        client.get(key)() match {
          case Response(Some(value)) => println(s"Found value [$value] for key [$key]")
          case Response(None) => println(s"No value found for key [$key]")
        }
      }
      case "delete" :: key :: _ => {
        client.delete(key)()
        println(s"Deleted value at [$key], if it existed")
      }
      case _ => {
        println("Couldn't understand that request!")
      }
    }
  }
}
