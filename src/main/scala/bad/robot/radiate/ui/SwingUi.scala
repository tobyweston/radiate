package bad.robot.radiate.ui

import java.awt.AWTEvent.KEY_EVENT_MASK
import java.awt.Toolkit.getDefaultToolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent._
import javax.swing.SwingUtilities.invokeLater
import javax.swing.UIManager.getSystemLookAndFeelClassName
import javax.swing._

import bad.robot.radiate.FunctionInterfaceOps.{toRunnable, toSupplier}
import bad.robot.radiate.monitor.MonitoringTasksFactory._
import bad.robot.radiate.monitor._
import bad.robot.radiate.teamcity.SanitisedException

class SwingUiS(factory: FrameFactoryS) extends Ui with ObserverS {
  
  private val frames = new StatusFramesS(factory)
  private val console = new Console(frames.primary)

  setupGlobalEventListeners()
  setLookAndFeel()

  private def setupGlobalEventListeners() {
    addAwtEventListener(new ExitOnEscape)
    addAwtEventListener(new SwitchTo(FrameFactory.desktopMode, VK_D))
    addAwtEventListener(new SwitchTo(FrameFactory.fullScreen, VK_F))
    addAwtEventListener(new ToggleConsoleDialog(console))
    addAwtEventListener(new Restart(singleAggregate, VK_A))
    addAwtEventListener(new Restart(multipleProjects, VK_C))
    addAwtEventListener(new Restart(multipleBuildsDemo, VK_X))
  }

  private def addAwtEventListener(listener: AWTEventListener) {
    getDefaultToolkit.addAWTEventListener(listener, KEY_EVENT_MASK)
  }

  private def setLookAndFeel() {
    UIManager.setLookAndFeel(getSystemLookAndFeelClassName)
  }

  def start() {
    frames.display
  }

  def stop() {
    frames.dispose
    getDefaultToolkit.getAWTEventListeners.foreach(listener => getDefaultToolkit.removeAWTEventListener(listener))
  }

  def createStatusPanels: List[ObserverS] = {
    frames.createStatusPanels
  }

  override def update(source: ObservableS, information: InformationS) {
    invokeLater(console.append(s"$information"))
  }

  override def update(source: ObservableS, exception: Exception) {
    val message = new SanitisedException(exception).getMessage
    invokeLater(console.append(s"$message when monitoring ${if (source == null) "" else source.toString}"))
  }
}