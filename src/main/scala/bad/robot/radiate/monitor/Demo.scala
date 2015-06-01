package bad.robot.radiate.monitor

import bad.robot.radiate.monitor.DemonstrativeMonitorS.BusyMonitorExampleS
import bad.robot.radiate.monitor.RandomStatusS._
import bad.robot.radiate._

private class DemoMonitoringTaskS extends ThreadSafeObservableS with MonitoringTaskS {
  private var monitor: DemonstrativeMonitorS = new BusyMonitorExampleS

  def run {
    monitor = monitor.notify(this)
  }

  override def toString = "demonstration"
}

private trait DemonstrativeMonitorS {
  def notify(observable: ObservableS): DemonstrativeMonitorS
}

private object DemonstrativeMonitorS {
  class BusyMonitorExampleS extends DemonstrativeMonitorS {
    def notify(observable: ObservableS): DemonstrativeMonitorS = {
      observable.notifyObservers(Busy, new NullProgressS)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new InformationS(s"Example busy monitor with random status $status"))
      new IdleMonitorExampleS
    }
  }

  class IdleMonitorExampleS extends DemonstrativeMonitorS {
    def notify(observable: ObservableS): DemonstrativeMonitorS = {
      observable.notifyObservers(Idle, new NullProgressS)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new InformationS(s"Example of an idle monitor with random status of $status"))
      new ProgressingMonitorExampleS
    }
  }

  class ProgressingMonitorExampleS extends DemonstrativeMonitorS {
    def notify(observable: ObservableS): DemonstrativeMonitorS = {
      observable.notifyObservers(Progressing, randomProgress)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new InformationS(s"Example of a progressing monitor with random status $status"))
      new OvertimeMonitorExampleS
    }
  }

  private class CompleteS extends ProgressS(100, 100)

  class OvertimeMonitorExampleS extends DemonstrativeMonitorS {
    def notify(observable: ObservableS): DemonstrativeMonitorS = {
      observable.notifyObservers(Progressing, new CompleteS)
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new InformationS(s"Example of an overtime monitor with random status of $status"))
      new ErrorExampleS
    }
  }

  class ErrorExampleS extends DemonstrativeMonitorS {
    def notify(observable: ObservableS): DemonstrativeMonitorS = {
      observable.notifyObservers(Busy, new NullProgressS)
      observable.notifyObservers(new RuntimeException("An exception exception"))
      val status = randomStatus
      observable.notifyObservers(status)
      observable.notifyObservers(new InformationS(s"Example of an error with random status of $status"))
      new IdleMonitorExampleS
    }
  }
}

class DemoS extends ThreadSafeObservableS with MonitoringTasksFactoryS {
  def create: List[MonitoringTaskS] = List[MonitoringTaskS](new DemoMonitoringTaskS)
}