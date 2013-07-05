package com.hopper.scala.school.geocode

import com.twitter.finagle.Service
import com.twitter.util.Future
import com.hopper.scala.school.geocode.DataModel._


class GeocodingCitySearch(geocoder: Service[String, GeocodeResponse]) extends Service[String, City] {

  import GeocodingCitySearch._

  def apply(request: String): Future[City] = geocoder(request).map(toCity)
}

object GeocodingCitySearch {

  def toCity(response: GeocodeResponse) = {

//    response.results.view
//      .flatMap { result =>
//
//        def findByType(typ: String) = result.address_components.find { _.types contains typ }.map { _.long_name }
//
//        for {
//          name <- findByType("locality")
//          country <- findByType("country")
//        } yield City(name, country, result.geometry.location)
//      }
//      .headOption
//      .getOrElse { throw new IllegalStateException("Could not find a valid city!") }

    // TODO: Fix this
    City(
      name = "Atlantis",
      country = "Atlantic Ocean",
      location = Coord(0, 0)
    )
  }
}
