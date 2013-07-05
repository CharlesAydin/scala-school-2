package com.hopper.scala.school.cache

import com.twitter.util.{FuturePool, Future}
import scala.collection.JavaConverters._
import java.util.concurrent.{Executors, ConcurrentHashMap}

/**
 * A simple implementation of the distributed map interface using a concurrent in-memory
 * map and a thread pool.
 */
class MemoryCache extends Cache.FutureIface {

  val NumThreads = 4

  val map = new ConcurrentHashMap[String, String](NumThreads).asScala

  val futurePool = FuturePool(Executors.newFixedThreadPool(NumThreads))

  def get(key: String): Future[Response] = futurePool {
    Response(value = map.get(key))
  }

  def put(key: String, value: String): Future[Unit] = futurePool {
    map.put(key, value)
  }

  def delete(key: String): Future[Unit] = futurePool {
    map.remove(key)
  }
}
