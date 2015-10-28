package balloon.reading.types

class LocationSpec extends FlatUnitSpec with ExpectedLogging {

  "A Location" should "be loggable" in {
    forAll { (a: Int, b:Int) =>
      Location(a, b).toLogFormat shouldEqual expectedFormat(Location(a, b))
    }
  }
}
