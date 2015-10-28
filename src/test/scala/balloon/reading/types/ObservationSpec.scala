package balloon.reading.types

import java.time.LocalDateTime
import java.util.Locale

import org.scalacheck.Gen

class ObservationSpec extends FlatUnitSpec with ParameterChoices with ExpectedLogging {

  val locations: Gen[Location] = for (x <- allInts; y <- allInts) yield Location(x, y)
  val temperatures: Gen[Temperature] = for (n <- mercuryValues) yield Temperature(n)
  val observatories: Gen[ObservatoryCode] = for (o <- Gen.oneOf(Locale.getISOCountries.toList)) yield ObservatoryCode(o)

  "An observation" should "be loggable" in {
    forAll (timestamps, locations, temperatures, observatories) {
      (timestamp: LocalDateTime, location: Location, temperature: Temperature, observatory: ObservatoryCode) => {
        val observation = Observation(Some(Timestamp(timestamp)), Some(location), Some(temperature), Some(observatory))
        observation.toLogFormat shouldEqual expectedFormat(observation)
      }
    }
  }

  it should "allow for optional timestamp" in {
    forAll (locations, temperatures, observatories) {
      (location: Location, temperature: Temperature, observatory: ObservatoryCode) => {
        val observation = Observation(None, Some(location), Some(temperature), Some(observatory))
        observation.toLogFormat shouldEqual expectedFormat(observation)
      }
    }
  }

  it should "allow for optional location" in {
    forAll (timestamps, temperatures, observatories) {
      (timestamp: LocalDateTime, temperature: Temperature, observatory: ObservatoryCode) => {
        val observation = Observation(Some(Timestamp(timestamp)), None, Some(temperature), Some(observatory))
        observation.toLogFormat shouldEqual expectedFormat(observation)
      }
    }
  }

  it should "allow for optional temperature" in {
    forAll (timestamps, locations, observatories) {
      (timestamp: LocalDateTime, location: Location, observatory: ObservatoryCode) => {
        val observation = Observation(Some(Timestamp(timestamp)), Some(location), None, Some(observatory))
        observation.toLogFormat shouldEqual expectedFormat(observation)
      }
    }
  }

  it should "allow for optional observatory code" in {
    forAll (timestamps, locations, temperatures) {
      (timestamp: LocalDateTime, location: Location, temperature: Temperature) => {
        val observation = Observation(Some(Timestamp(timestamp)), Some(location), Some(temperature), None)
        observation.toLogFormat shouldEqual expectedFormat(observation)
      }
    }
  }

}
