package bad.robot.radiate.ui

import java.awt.AWTEvent.KEY_EVENT_MASK
import java.awt.Toolkit.getDefaultToolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent._
import javax.swing.SwingUtilities.invokeLater
import javax.swing.UIManager.getSystemLookAndFeelClassName
import javax.swing._

import bad.robot.radiate.FunctionInterfaceOps.toRunnable
import bad.robot.radiate.monitor.MonitoringTasksFactoryS._
import bad.robot.radiate.monitor._
import bad.robot.radiate.teamcity.SanitisedExceptionS
import bad.robot.radiate.ui.FrameFactoryS._

class SwingUiS(factory: FrameFactoryS) extends UiScala with ObserverS {
  
  private val frames = new StatusFramesScala(factory)
  private val console = new ConsoleS(frames.primary)

  setupGlobalEventListeners()
  setLookAndFeel()

  private def setupGlobalEventListeners() {
    addAwtEventListener(new ExitOnEscapeS)
    addAwtEventListener(new SwitchToS(desktopMode, VK_D))
    addAwtEventListener(new SwitchToS(fullScreen, VK_F))
    addAwtEventListener(new ToggleConsoleDialogS(console))
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

  def createStatusPanels: List[ObserverS] = {
    frames.createStatusPanels
  }

  override def update(source: ObservableS, information: InformationS) {
    invokeLater(console.append(s"$information"))
  }

  override def update(source: ObservableS, exception: Exception) {
    val message = new SanitisedExceptionS(exception).getMessage
    invokeLater(console.append(s"$message when monitoring ${if (source == null) "" else source.toString}"))
  }
}