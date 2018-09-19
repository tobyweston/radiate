package bad.robot.radiate.travis

import bad.robot.radiate._
import bad.robot.radiate.activity.{Busy, Idle}
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import com.google.code.tempusfugit.temporal.Duration.minutes
import simplehttp.HttpClients.anApacheClient
import simplehttp.MessageContent
import simplehttp.configuration.HttpTimeout.httpTimeout


class TravisProjectMonitor(project: Project) extends NonRepeatingObservable with MonitoringTask {

  private val http = anApacheClient.`with`(httpTimeout(minutes(2)))
  
  def run(): Unit = {
    notifyObservers(Busy, new NullProgress)
    val response = http.get(project.toUrl)
    val status = if (response.ok()) parseBuildStatus(response.getContent) else Unknown
    notifyObservers(status)
    notifyObservers(Idle, new NullProgress)
    notifyObservers(new Information(project.toUrl.toExternalForm))
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

  override def toString: String = s"${project.username.value} (${project.project} ${project.branch})"
}