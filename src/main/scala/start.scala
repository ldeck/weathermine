import java.io.{File, FileOutputStream, PrintStream}

import balloon.reading.log.ReadingsLogger
import balloon.reading.validation.ArgsValidation

object start extends App {

  val argsValidator = new ArgsValidation(args)
  if (!argsValidator.is_valid_?) {
    println(argsValidator.usage)
    sys.exit(1)
  }
  else
  {
    val logfile: File = argsValidator.logfile.get
    val raf = new FileOutputStream(logfile, logfile.exists())
    val ps = new PrintStream(raf, true, "UTF-8")

    try {
      val logger = new ReadingsLogger(ps, argsValidator.count.get, 0.1)
      logger.runWith(argsValidator.count.get)
    } finally {
      ps.close()
    }
  }
}
