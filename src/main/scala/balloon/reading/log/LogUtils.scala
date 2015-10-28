package balloon.reading.log

trait LogUtils {

  def logOption(loggable: Option[LogFormat]): String = {
    loggable.getOrElse(BLANK).toLogFormat
  }

  private [this] object BLANK extends LogFormat {
    override def toLogFormat = ""
  }

}
