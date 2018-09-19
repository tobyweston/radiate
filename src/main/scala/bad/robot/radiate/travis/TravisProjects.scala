package bad.robot.radiate.travis

import bad.robot.radiate.Error
import bad.robot.radiate.monitor._
import scalaz.{\/, \/-}

class TravisProjects extends ThreadSafeObservable with MonitoringTasksFactory {
  
  private val projects = List(
    "https://travis-ci.org/tobyweston/radiate.svg?branch=master",
    "https://travis-ci.org/tobyweston/radiate.svg?branch=master",
    "https://travis-ci.org/tobyweston/tempus-fugit.svg?branch=master",
    "https://travis-ci.org/tobyweston/temperature-machine.svg?branch=master"
  )
  
  def create: Error \/ List[MonitoringTask] = {
    \/-(projects.map(project => new TravisProjectMonitor(project)))
  }
  
  override def toString = "monitoring travis projects"
}
