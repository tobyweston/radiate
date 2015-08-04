package bad.robot.radiate.monitor

class MonitoringException(message: String) extends Exception(message)

class NothingToMonitorException extends MonitoringException("Nothing found to monitor, check your configuration")