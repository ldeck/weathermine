package balloon.reading.types

import java.time.LocalDateTime
import java.util.Locale

import org.scalacheck.Gen

trait ParameterChoices {

  val allInts = Gen.choose(Int.MinValue, Int.MaxValue)
  val allLongs = Gen.choose(Long.MinValue, Long.MaxValue)

  val countryCodesKnown = Observatory.values().map(_.name())
  val countryCodesUnknown = Locale.getISOCountries.filter(!countryCodesKnown.contains(_))

  val mercuryValues = Gen.choose(-100, 100)

  val years = Gen.choose(2000, 3000)
  val months = Gen.choose(1, 12)
  val days = Gen.choose(1, 31)
  val hours = Gen.choose(0, 23)
  val minutes = Gen.choose(0, 59)
  val seconds = Gen.choose(0, 59)

  val timestamps = for {
    year <- years
    month <- months
    day <- days
    hour <- hours
    minute <- minutes
    second <- seconds
  } yield {
      LocalDateTime.of(0, 1, 1, 0, 0, 0)
        .plusYears(year)
        .plusMonths(month - 1)
        .plusDays(day - 1)
        .plusHours(hour)
        .plusMinutes(minute)
        .plusSeconds(second)
    }
}
