package school.geocode

import spray.json.{DefaultJsonProtocol, JsonFormat}

object DataModel extends DefaultJsonProtocol {

  case class GeocodeResponse (
    results: Seq[GeocodeResult],
    status: String = "OK"
  )

  case class GeocodeResult(
    address_components: Seq[AddressComponent],
    formatted_address: String = "",
    geometry: Geometry,
    types: Set[String] = Set("locality")
  )

  case class AddressComponent(
    long_name: String,
    short_name: String,
    types: Set[String]
  )

  case class Geometry(
    location: Coord,
    location_type: String = "APPROXIMATE",
    viewport: Bounds = Bounds(Coord(90, 180), Coord(-90, -180))
  )

  case class Bounds(
    northeast: Coord,
    southwest: Coord
  )

  case class Coord(
    lat: Double,
    lng: Double
  )

  case class City(
    name: String,
    country: String,
    location: Coord
  )

  type Fmt[T] = JsonFormat[T] // Keep things concise
  implicit val coordFormat: Fmt[Coord] = jsonFormat2(Coord)
  implicit val boundsFormat: Fmt[Bounds] = jsonFormat2(Bounds)
  implicit val geoFormat: Fmt[Geometry] = jsonFormat3(Geometry)
  implicit val componentFormat: Fmt[AddressComponent] = jsonFormat3(AddressComponent)
  implicit val resFormat: Fmt[GeocodeResult] = jsonFormat4(GeocodeResult)
  implicit val respFormat: Fmt[GeocodeResponse] = jsonFormat2(GeocodeResponse)
  implicit val cityFormat: Fmt[City] = jsonFormat3(City)
}
