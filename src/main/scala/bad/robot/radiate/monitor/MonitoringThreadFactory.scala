package bad.robot.radiate.monitor

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class MonitoringThreadFactory extends ThreadFactory {
  private val threadCount = new AtomicInteger

  def newThread(runnable: Runnable): Thread = new Thread(runnable, "radiate-monitoring-thread-" + threadCount.getAndIncrement)
}