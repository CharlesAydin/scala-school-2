package school.cache

import com.twitter.server.TwitterServer
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.Thrift
import java.net.InetSocketAddress
import com.twitter.util.Await

/**
 * A simple finagle server that launches the cache service on the specified port.
 */
object CacheServer extends TwitterServer {

  val service = new Cache.FinagledService(
    new MemoryCache,
    new TBinaryProtocol.Factory()
  )

  val binding = flag("bind", new InetSocketAddress(9090), "The address to bind the thrift service to")

  def main() {

    val server = Thrift.serve(binding(), service)

    onExit {
      server.close()
    }

    Await.ready(server)
  }
}
