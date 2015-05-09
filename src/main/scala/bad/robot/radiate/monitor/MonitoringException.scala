package bad.robot.radiate.monitor

class MonitoringExceptionS(message: String) extends RuntimeException(message)

class NothingToMonitorExceptionS extends MonitoringException("Nothing found to monitor, check your configuration")