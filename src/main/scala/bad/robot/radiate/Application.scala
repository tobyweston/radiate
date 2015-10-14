package bad.robot.radiate

import java.util.concurrent.Executors.newScheduledThreadPool

import bad.robot.radiate.monitor._
import bad.robot.radiate.ui.{FrameFactory, SwingUi}

object Application {
  private val threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory)
}

class Application {
  private val logger = new LoggingObserver
  private val monitor: Monitor = new ScheduledMonitor(Application.threadPool)
  
  private var currentTasks: MonitoringTasksFactory = null
  private var currentFrames: FrameFactory = null
  private var monitoring: MonitoringTasks = null
  private var ui: SwingUi = null

  def start(tasks: MonitoringTasksFactory, frames: FrameFactory) {
    currentTasks = tasks
    currentFrames = frames
    ui = new SwingUi(frames)
    tasks.addObservers(logger, ui)
    monitoring = new MonitoringTasks(tasks, monitor)
    monitoring.foreach(monitor => {
      monitor.addObservers(ui.createStatusPanels)
      monitor.addObservers(ui, logger)
    })
    monitoring.start()
    ui.start()
    addShutdownHook()
  }

  def stop() {
    monitoring.foreach(observable => observable.removeAllObservers())
    currentTasks.removeAllObservers()
    monitoring.stop()
    ui.stop()
  }

  private def addShutdownHook() {
    Runtime.getRuntime.addShutdownHook(new Thread(new Runnable(){
      override def run(): Unit = monitor.stop
    }))
  }

  def getCurrentFrames: FrameFactory = currentFrames

  def getCurrentTasks: MonitoringTasksFactory = currentTasks
}