package balloon.reading.log

import java.time.LocalDateTime
import java.util.Locale

import balloon.reading.types._

import scala.util.Random

trait BalloonLogging {

  def is_flakey_? : Boolean

  val random = new Random

  private val year2k = LocalDateTime.of(2000, 1, 1, 0, 0, 0)
  private val second_range = 500 * 1000 * 1000

  def randomTimestamp: Option[Timestamp] = {
    val n = random.nextInt(second_range)
    val c = year2k.plusSeconds(n)
    val t = Timestamp(c)
    flakey_?(t)
  }

  def randomLocation: Option[Location] = {
    val x = random.nextInt()
    val y = random.nextInt()
    val l = Location(x, y)
    flakey_?(l)
  }

  def randomTemperature: Option[Temperature] = {
    val mercury = random.nextInt(100)
    val value = if (random.nextBoolean()) mercury else mercury * -1
    val t = Temperature(value)
    flakey_?(t)
  }

  private val countryCodes = Locale.getISOCountries
  def randomObservatory: Option[ObservatoryCode] = {
    val i = random.nextInt(countryCodes.length)
    val code = countryCodes(i)
    val o = ObservatoryCode(code)
    flakey_?(o)
  }

  def randomObservation: Option[Observation] = {
    val o = Observation(randomTimestamp, randomLocation, randomTemperature, randomObservatory)
    flakey_?(o)
  }

  private [this] def flakey_?[T](t: T): Option[T] = {
    if (is_flakey_?)
      None
    else
      Some(t)
  }
}
