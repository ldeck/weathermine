package balloon.reading.log

import java.io.PrintStream

class ReadingsLogger(val out: PrintStream, val maxLogs: Long, val flakiness: Double) extends LogUtils with BalloonLogging {

  override def is_flakey_? = random.nextDouble() <= flakiness

  def runWith(maxLogs: Long): Unit = {
    (0L until maxLogs) foreach { (n: Long) =>
      logOption(randomObservation)
    }
  }

  override def logOption(loggable: Option[LogFormat]): String = {
    val line = super.logOption(loggable)
    out.println(line)
    line
  }
}
