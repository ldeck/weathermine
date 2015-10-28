package balloon.reading.validation

import java.io.File

class ArgsValidation(val argv: Array[String]) {

  lazy val count: Option[Long] = {
    if (isPositiveLong(argv(0))) {
      Some(argv(0).toLong)
    } else {
      None
    }
  }

  lazy val logfile: Option[File] = {
    val f = argv(1)
    if (isValidPath(f)) {
      Some(new File(f).getAbsoluteFile)
    } else {
      None
    }
  }

  def is_valid_? : Boolean = {
    argv.length == 2 && count.isDefined && logfile.isDefined
  }

  def usage: String = {
    """Usage: <count> <logfile>
      |
      | count: number of log entries to produce
      | logfile: the path to the new or existing logfile to append to
    """.stripMargin
  }

  private def isPositiveLong(s: String): Boolean = {
    s.matches("^[1-9][0-9]*$")
  }

  private def isValidPath(path: String): Boolean = {
    try {
      val logfile = new File(path)
      val f = logfile.getAbsoluteFile
      val p = f.getParentFile

      return p.isDirectory &&
        (f.isFile && f.canWrite ||
          !f.exists() && p.canWrite)
    }
    catch {
      case e: Exception =>
        Console.err.println(e.getMessage)
        Console.err.println(e)
    }
    false
  }
}
