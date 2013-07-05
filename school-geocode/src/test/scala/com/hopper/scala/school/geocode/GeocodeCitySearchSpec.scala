package com.hopper.scala.school.geocode

import org.specs2.mutable._
import com.hopper.scala.school.geocode.DataModel._

class GeocodeCitySearchSpec extends Specification {

  "A city geocoding service" should {

    "handle a geocoding response correctly" in {

      import GeocodingCitySearch.toCity

      "return a found city" in {

        val example = GeocodeResponse(
          results = Seq(
            GeocodeResult(
              address_components = Seq(
                AddressComponent("Madrid","Madrid", Set("locality")),
                AddressComponent("Spain", "ES", Set("country"))
              ),
              geometry = Geometry(
                location = Coord(5,5)
              )
            )
          )
        )

        toCity(example) must_== City("Madrid", "Spain", Coord(5,5))
      }
      
      "skip over non-cities" in {
        
        val example = GeocodeResponse(
          results = Seq(
            GeocodeResult(
              address_components = Seq(
                AddressComponent("Mexico", "MX",Set("country"))
              ),
              geometry = Geometry(
                location = Coord(5,5)
              )
            ),
            GeocodeResult(
              address_components = Seq(
                AddressComponent("Mexico", "Mexico",Set("locality")),
                AddressComponent("United States", "US",Set("country"))
              ),
              geometry = Geometry(
                location = Coord(5,5)
              )
            )
          )
        )

        toCity(example) must_== City("Mexico", "United States", Coord(5,5))
      }

      "fail when nothing found" in {

        val example = GeocodeResponse(
          results = Seq()
        )

        toCity(example) must throwAn[Exception]
      }

      "fail when no result is a city" in {

        val example = GeocodeResponse(
          results = Seq(
            GeocodeResult(
              address_components = Seq(
                AddressComponent("Spain", "ES", Set("country"))
              ),
              geometry = Geometry(
                location = Coord(5,5)
              )
            )
          )
        )

        toCity(example) must throwAn[Exception]
      }
    }
  }
}
