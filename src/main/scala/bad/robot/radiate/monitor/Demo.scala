package bad.robot.radiate.monitor

import bad.robot.radiate.monitor.DemonstrativeMonitor.BusyMonitorExample
import bad.robot.radiate.monitor.RandomStatus._
import bad.robot.radiate._

private class DemoMonitoringTask extends ThreadSafeObservable with MonitoringTask {
  private var monitor: DemonstrativeMonitor = new BusyMonitorExample

  def run {
    monitor = monitor.notify(this)
  }

  override def toString = "demonstration"
}

private trait DemonstrativeMonitor {
  def notify(observable: Observable): DemonstrativeMonitor
}

private object DemonstrativeMonitor {
  class BusyMonitorExample extends DemonstrativeMonitor {
    def notify(observable: Observable): DemonstrativeMonitor = {
      observable.notifyObservers(Busy, new NullProgress)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new Information(s"Example busy monitor with random status $status"))
      new IdleMonitorExample
    }
  }

  class IdleMonitorExample extends DemonstrativeMonitor {
    def notify(observable: Observable): DemonstrativeMonitor = {
      observable.notifyObservers(Idle, new NullProgress)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new Information(s"Example of an idle monitor with random status of $status"))
      new ProgressingMonitorExample
    }
  }

  class ProgressingMonitorExample extends DemonstrativeMonitor {
    def notify(observable: Observable): DemonstrativeMonitor = {
      observable.notifyObservers(Progressing, randomProgress)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new Information(s"Example of a progressing monitor with random status $status"))
      new OvertimeMonitorExample
    }
  }

  private class Complete extends Progress(100, 100)

  class OvertimeMonitorExample extends DemonstrativeMonitor {
    def notify(observable: Observable): DemonstrativeMonitor = {
      observable.notifyObservers(Progressing, new Complete)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new Information(s"Example of an overtime monitor with random status of $status"))
      new ErrorExample
    }
  }

  class ErrorExample extends DemonstrativeMonitor {
    def notify(observable: Observable): DemonstrativeMonitor = {
      observable.notifyObservers(Busy, new NullProgress)
      observable.notifyObservers(new RuntimeException("An exception exception"))
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new Information(s"Example of an error with random status of $status"))
      new IdleMonitorExample
    }
  }
}

class Demo extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: List[MonitoringTask] = List[MonitoringTask](new DemoMonitoringTask)
}