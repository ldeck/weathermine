package balloon.reading.types

class ObservatoryCodeSpec extends FlatUnitSpec with ExpectedLogging {

  "An observatory code" should "be loggable" in {
    forAll { (s: String) =>
      ObservatoryCode(s).toLogFormat shouldEqual expectedFormat(ObservatoryCode(s))
    }
  }
}
