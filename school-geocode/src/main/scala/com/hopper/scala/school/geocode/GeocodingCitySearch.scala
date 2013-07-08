package school.geocode

import com.twitter.finagle.Service
import com.twitter.util.Future
import school.geocode.DataModel._


class GeocodingCitySearch(geocoder: Service[String, GeocodeResponse]) extends Service[String, City] {

  import GeocodingCitySearch._

  def apply(request: String): Future[City] = geocoder(request).map(toCity)
}

object GeocodingCitySearch {

  def toCity(response: GeocodeResponse) = {
    // TODO: Fix this
    City(
      name = "Atlantis",
      country = "Atlantic Ocean",
      location = Coord(0, 0)
    )
  }
}
