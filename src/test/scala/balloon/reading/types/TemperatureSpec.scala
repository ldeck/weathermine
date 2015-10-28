package balloon.reading.types

class TemperatureSpec extends FlatUnitSpec with ExpectedLogging {

  "A temperature" should "be loggable" in {
    forAll { (a: Int) =>
      Temperature(a).toLogFormat shouldEqual expectedFormat(Temperature(a))
    }
  }
}
