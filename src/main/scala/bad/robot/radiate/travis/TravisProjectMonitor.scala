package bad.robot.radiate.travis

import bad.robot.radiate.activity.{Busy, Idle}
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import bad.robot.radiate._
import com.google.code.tempusfugit.temporal.Duration.minutes
import simplehttp.HttpClients.anApacheClient
import simplehttp.configuration.HttpTimeout.httpTimeout
import simplehttp.{MessageContent, Url}


class TravisProjectMonitor(projectUrl: String) extends NonRepeatingObservable with MonitoringTask {

  private val http = anApacheClient.`with`(httpTimeout(minutes(2)))
  
  def run(): Unit = {
    notifyObservers(Busy, new NullProgress)
    val response = http.get(Url.url(projectUrl))
    val status = if (response.ok()) parseBuildStatus(response.getContent) else Unknown
    notifyObservers(status)
    notifyObservers(Idle, new NullProgress)
    notifyObservers(new Information(projectUrl))
  }
  
  private def parseBuildStatus(content: MessageContent): Status = {
    val body = content.asString()
    if (body.contains("pass"))
      Ok
    else if (body.contains("fail"))
      Broken
    else
      Unknown
  }
}