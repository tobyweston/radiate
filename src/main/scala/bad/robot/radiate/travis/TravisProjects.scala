package bad.robot.radiate.travis

import bad.robot.radiate.Error
import bad.robot.radiate.config.Username
import bad.robot.radiate.monitor._
import scalaz.{\/, \/-}

class TravisProjects extends ThreadSafeObservable with MonitoringTasksFactory {
  
  private val projects = List(
    Project(Username("tobyweston"), "radiate"),
    Project(Username("simple-http"), "simple-http"),
    Project(Username("tobyweston"), "BasicWebApp"),
    Project(Username("tobyweston"), "temperature-machine")
  )
  
  def create: Error \/ List[MonitoringTask] = {
    \/-(projects.map(project => new TravisProjectMonitor(project)))
  }
  
  override def toString = "monitoring travis projects"
}
