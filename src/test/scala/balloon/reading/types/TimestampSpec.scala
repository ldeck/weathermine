package balloon.reading.types

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimestampSpec extends FlatUnitSpec with ParameterChoices {

  "A timestamp" should "be loggable" in {
    forAll (timestamps) { (t: LocalDateTime) =>
      val stamp = Timestamp(t)
      stamp.toLogFormat shouldEqual DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(t)
    }
  }
}
