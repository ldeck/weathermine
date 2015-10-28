package balloon.reading.types

import balloon.reading.types.Observatory.{AU, FR, Other, US}
import org.scalacheck.Gen

class ObservatorySpec extends FlatUnitSpec with ParameterChoices {

  "AU" should "have celsius scale" in {
    AU.scale shouldEqual TemperatureScale.celsius
  }

  it should "have distanceUnit kms" in {
    AU.distanceUnit shouldEqual DistanceUnit.km
  }

  "US" should "have fahrenheit scale" in {
    US.scale shouldEqual TemperatureScale.fahrenheit
  }

  it should "have distanceUnit miles" in {
    US.distanceUnit shouldEqual DistanceUnit.miles
  }

  "FR" should "have kelvin scale" in {
    FR.scale shouldEqual TemperatureScale.kelvin
  }

  it should "have distanceUnit meters" in {
    FR.distanceUnit shouldEqual DistanceUnit.m
  }

  "Other" should "have kelvin scale" in {
    Other.scale shouldEqual TemperatureScale.kelvin
  }

  it should "have distanceUnit kms" in {
    Other.distanceUnit shouldEqual DistanceUnit.km
  }

  "All of them" should "be found by code" in {
    val options = Gen.oneOf(Observatory.values())

    forAll (options) { (o) =>
      Observatory.findByCode(o.name()) shouldEqual o
    }
  }

  they should "be found by code even if lowercase" in {
    val options = Gen.oneOf(Observatory.values())

    forAll (options) { (o) =>
      Observatory.findByCode(o.name().toLowerCase) shouldEqual o
    }
  }

  "Unknown codes" should "default to Other" in {
    val observatories: Gen[ObservatoryCode] = for (o <- Gen.oneOf(countryCodesUnknown)) yield ObservatoryCode(o)
    forAll (observatories) { (o) =>
      Observatory.findByCode(o.code) shouldEqual Other
    }
  }

}
