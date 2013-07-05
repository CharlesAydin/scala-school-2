package com.hopper.scala.school.cache

import org.specs2.mutable._

class MemoryCacheSpec extends Specification {

  "an in-memory cache implementation" should {

    val Key = "key"
    val Data = "data"

    "read its own writes" in {
      val cache = new MemoryCache

      val result = for {
        _ <- cache.put(Key, Data)
        got <- cache.get(Key)
      } yield got

      result() must_== Response(value = Some(Data))
    }

    "delete correctly" in {
      val cache = new MemoryCache

      val result = for {
        _ <- cache.put(Key, Data)
        _ <- cache.delete(Key)
        got <- cache.get(Key)
      } yield got

      result() must_== Response(value = None)
    }
  }
}
