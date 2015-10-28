package balloon.reading.log

import balloon.reading.types.{ParameterChoices, FlatUnitSpec}

class BalloonLoggingSpec extends FlatUnitSpec with ParameterChoices {

  val flakyLogger = logger(flaky = true)
  val happyLogger = logger(flaky = false)

  "randomLocation" should "be defined when flaky is false" in {
    forAll (allLongs, allLongs) { (x, y) =>
      happyLogger.randomLocation.isDefined shouldEqual true
    }
  }

  it should "be undefined when flaky is true" in {
    forAll (allLongs, allLongs) { (x, y) =>
      flakyLogger.randomLocation.isDefined shouldEqual false
    }
  }

  "randomObservation" should "be defined when flakey is false" in {
    forAll (allInts) { (t) =>
      happyLogger.randomObservation.isDefined shouldEqual true
    }
  }

  it should "be undefined when flaky is true" in {
    forAll (allInts) { (t) =>
      flakyLogger.randomObservation.isDefined shouldEqual false
    }
  }

  "randomObservatory" should "be defined when flaky is false" in {
    forAll (allInts) { (t) =>
      happyLogger.randomObservatory.isDefined shouldEqual true
    }
  }

  it should "be undefined when flaky is true" in {
    forAll (allInts) { (t) =>
      flakyLogger.randomObservatory.isDefined shouldEqual false
    }
  }

  "randomTemperature" should "be defined when flaky is false" in {
    forAll (mercuryValues) { (t) =>
      happyLogger.randomTemperature.isDefined shouldEqual true
    }
  }

  it should "not be defined when flaky is true" in {
    forAll (allInts) { (t) =>
      flakyLogger.randomTemperature.isDefined shouldEqual false
    }
  }

  "randomTimestamp" should "be defined when flaky is false" in {
    forAll (allInts) { (t) =>
      happyLogger.randomTemperature.isDefined shouldEqual true
    }
  }

  it should "be undefined when flaky is true" in {
    forAll (allInts) { (t) =>
      flakyLogger.randomTemperature.isDefined shouldEqual false
    }
  }

  private def logger(flaky: Boolean): BalloonLogging = {
    new BalloonLogging {
      override def is_flakey_? : Boolean = flaky
    }
  }
}
