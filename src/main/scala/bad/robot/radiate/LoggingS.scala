package bad.robot.radiate

import java.io.File

import org.apache.log4j._

object LoggingS {

  def initialise() {
    val root = Logger.getRootLogger
    root.addAppender(createFileAppender)
    root.addAppender(createConsoleAppender)
  }

  private def createFileAppender: FileAppender = {
    val file = new RollingFileAppender
    file.setName("File Appender")
    file.setMaxBackupIndex(1)
    file.setMaxFileSize("2MB")
    file.setFile(System.getProperty("user.home") + File.separator + ".radiate" + File.separator + "radiate.log")
    file.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"))
    file.setThreshold(Level.INFO)
    file.setAppend(true)
    file.activateOptions()
    file
  }

  private def createConsoleAppender: ConsoleAppender = {
    val console = new ConsoleAppender
    console.setLayout(new PatternLayout("%d [%p|%c{1}] %m%n"))
    console.setThreshold(Level.INFO)
    console.activateOptions()
    console
  }
}
