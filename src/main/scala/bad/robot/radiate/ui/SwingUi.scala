package bad.robot.radiate.ui

import java.awt.AWTEvent.KEY_EVENT_MASK
import java.awt.Toolkit.getDefaultToolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent._
import javax.swing.SwingUtilities.invokeLater
import javax.swing.UIManager.getSystemLookAndFeelClassName
import javax.swing._

import bad.robot.radiate.FunctionInterfaceOps.toRunnable
import bad.robot.radiate.monitor.MonitoringTasksFactory._
import bad.robot.radiate.monitor._
import bad.robot.radiate.teamcity.SanitisedException
import bad.robot.radiate.ui.FrameFactory._

class SwingUi(factory: FrameFactory) extends Ui with Observer {
  
  private val frames = new StatusFrames(factory)
  private val console = new Console(frames.primary)

  setupGlobalEventListeners()
  setLookAndFeel()

  private def setupGlobalEventListeners() {
    addAwtEventListener(new ExitOnEscape)
    addAwtEventListener(new SwitchTo(desktopMode, VK_D))
    addAwtEventListener(new SwitchTo(fullScreen, VK_F))
    addAwtEventListener(new ToggleConsoleDialog(console))
    addAwtEventListener(new RestartS(singleAggregate, VK_A))
    addAwtEventListener(new RestartS(multipleProjects, VK_C))
    addAwtEventListener(new RestartS(multipleBuildsDemo, VK_X))
  }

  private def addAwtEventListener(listener: AWTEventListener) {
    getDefaultToolkit.addAWTEventListener(listener, KEY_EVENT_MASK)
  }

  private def setLookAndFeel() {
    UIManager.setLookAndFeel(getSystemLookAndFeelClassName)
  }

  def start() {
    frames.display()
  }

  def stop() {
    frames.dispose()
    getDefaultToolkit.getAWTEventListeners.foreach(listener => getDefaultToolkit.removeAWTEventListener(listener))
  }

  def createStatusPanels: List[Observer] = {
    frames.createStatusPanels
  }

  override def update(source: Observable, information: Information) {
    invokeLater(console.append(s"$information"))
  }

  override def update(source: Observable, exception: Exception) {
    val message = new SanitisedException(exception).getMessage
    invokeLater(console.append(s"$message when monitoring ${if (source == null) "" else source.toString}"))
  }
}