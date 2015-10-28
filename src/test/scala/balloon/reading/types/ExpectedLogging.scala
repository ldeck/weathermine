package balloon.reading.types

import java.time.format.DateTimeFormatter

trait ExpectedLogging {

  protected def expectedFormat(timestamp: Timestamp): String = {
    DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(timestamp.clock)
  }

  protected def expectedFormat(location: Location): String = {
    s"${location.x},${location.y}"
  }

  protected def expectedFormat(temperature: Temperature): String = {
    s"${temperature.reading}"
  }

  protected def expectedFormat(observatoryCode: ObservatoryCode): String = {
    observatoryCode.code.toUpperCase
  }

  protected def expectedFormat(string: String): String = string

  protected def expectedFormat(observation: Observation): String = {
    val tf = if (observation.timestamp.isDefined) expectedFormat(observation.timestamp.get) else ""
    val lf = if (observation.location.isDefined) expectedFormat(observation.location.get) else ""
    val cf = if (observation.temperature.isDefined) expectedFormat(observation.temperature.get) else ""
    val of = if (observation.observatory.isDefined) expectedFormat(observation.observatory.get) else ""

    s"$tf|$lf|$cf|$of"
  }
}
