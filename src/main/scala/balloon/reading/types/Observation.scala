package balloon.reading.types

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import balloon.reading.log.LogFormat
import balloon.reading.log.LogUtils

case class Timestamp(clock: LocalDateTime) extends LogFormat {
  private val TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  override def toLogFormat = TIMESTAMP_FORMAT.format(clock)
}
case class Location(x: Int, y: Int) extends LogFormat {
  override def toLogFormat = s"$x,$y"
}
case class Temperature(reading: Int) extends LogFormat {
  override def toLogFormat = reading.toString
}
case class ObservatoryCode(code: String) extends LogFormat {
  override def toLogFormat: String = code.toUpperCase
}

case class Observation(timestamp: Option[Timestamp],
                       location: Option[Location],
                       temperature: Option[Temperature],
                       observatory: Option[ObservatoryCode]) extends LogFormat with LogUtils
{
  override def toLogFormat = {
    "%s|%s|%s|%s".format(
      logOption(timestamp),
      logOption(location),
      logOption(temperature),
      logOption(observatory)
    )
  }
}
