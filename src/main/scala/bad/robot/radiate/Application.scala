package bad.robot.radiate

import java.util.concurrent.Executors.newScheduledThreadPool

import bad.robot.radiate.monitor._
import bad.robot.radiate.ui.{FrameFactoryS, SwingUiS}

object ApplicationS {
  private val threadPool = newScheduledThreadPool(5, new MonitoringThreadFactoryS)
}

class ApplicationS {
  private val logger = new LoggingObserverS
  private val monitor: MonitorS = new ScheduledMonitorS(ApplicationS.threadPool)
  
  private var currentTasks: MonitoringTasksFactoryS = null
  private var currentFrames: FrameFactoryS = null
  private var monitoring: MonitoringTasksS = null
  private var ui: SwingUiS = null

  def start(tasks: MonitoringTasksFactoryS, frames: FrameFactoryS) {
    currentTasks = tasks
    currentFrames = frames
    ui = new SwingUiS(frames)
    tasks.addObservers(logger, ui)
    monitoring = new MonitoringTasksS(tasks, monitor)
    monitoring.foreach(monitor => {
      monitor.addObservers(ui.createStatusPanels)
      monitor.addObservers(ui, logger)
    })
    monitoring.start()
    ui.start()
    addShutdownHook()
  }

  def stop() {
    monitoring.foreach(observable => observable.removeAllObservers)
    currentTasks.removeAllObservers
    monitoring.stop()
    ui.stop()
  }

  private def addShutdownHook() {
    Runtime.getRuntime.addShutdownHook(new Thread(new Runnable(){
      override def run(): Unit = monitor.stop
    }))
  }

  def getCurrentFrames: FrameFactoryS = {
    currentFrames
  }

  def getCurrentTasks: MonitoringTasksFactoryS = {
    currentTasks
  }
}