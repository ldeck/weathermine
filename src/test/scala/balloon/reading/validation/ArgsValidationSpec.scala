package balloon.reading.validation

import java.io.File

import balloon.reading.types.FlatUnitSpec
import org.scalacheck.Gen

class ArgsValidationSpec extends FlatUnitSpec {

  val positiveInts = Gen.chooseNum(1, Int.MaxValue)
  val negativeInts = Gen.chooseNum(Int.MinValue, 0)

  "usage" should "be \"Usage: <count> <logfile>\"" in new ArgsValidation(Array()) {
    val expectedText =
    """Usage: <count> <logfile>
      |
      | count: number of log entries to produce
      | logfile: the path to the new or existing logfile to append to
    """.stripMargin

    val actualText = usage
    actualText shouldEqual expectedText
  }

  "validation" should "be false for ''" in new ArgsValidation(Array()) {
    is_valid_? shouldEqual false
  }

  it should "be false for '[count]'" in {
    forAll { (n: Int) =>
      new ArgsValidation(Array(n.toString)).is_valid_? shouldEqual false
    }
  }

  it should "be false for 0 or -ve(n) in '[count] [logfile]'" in {
    forAll (negativeInts) { (n: Int) =>
      new ArgsValidation(Array(n.toString, "logfile.log")).is_valid_? shouldEqual false
    }
  }

  it should "be false for non-numeric '[count] [logfile]'" in {
    forAll { (s: String) =>
      new ArgsValidation(Array(s, "logfile.log")).is_valid_? shouldEqual false
    }
  }

  it should "be false for '[count >= 1] [logfile = notexistsdir/logfile.log]'" in {
    forAll (positiveInts) { (n: Int) =>
      new ArgsValidation(Array(n.toString, "notexistsdir/logfile.log")).is_valid_? shouldEqual false
    }
  }

  it should "be false for '[count >= 1] [logfile = target/]'" in {
    forAll (positiveInts) { (n: Int) =>
      new ArgsValidation(Array(n.toString, "target/")).is_valid_? shouldEqual false
    }
  }

  it should "be false for '[count >= 1] [logfile = nonwriteable/logfile.log]'" in {
    forAll (positiveInts) { (n: Int) =>
      new ArgsValidation(Array(n.toString, "/var/log/test.log")).is_valid_? shouldEqual false
    }
  }

  it should "be true for [count >= 1] <logfile = existingdir/newfile.log]" in {
    forAll (positiveInts) { (n: Int) =>
      new ArgsValidation(Array(n.toString, "newfile.log")).is_valid_? shouldEqual true
    }
  }

  it should "be true for [count >= 1] <logfile = existingdir/existing.log]" in {
    val f = File.createTempFile("balloon", "log")
    try {
      forAll(positiveInts) { (n: Int) =>
        new ArgsValidation(Array(n.toString, f.getAbsolutePath)).is_valid_? shouldEqual true
      }
    }
    finally {
      f.deleteOnExit()
    }
  }

}
