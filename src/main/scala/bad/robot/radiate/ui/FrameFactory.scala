package bad.robot.radiate.ui

import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment

trait FrameFactory {
  def create: List[StatusFrame]
}

object FrameFactory {

  def fullScreen = new FrameFactory {
    def create = {
      val screens = getLocalGraphicsEnvironment.getScreenDevices
      val frames = (0 until screens.length).map(index => new StatusFrame(index, new FullScreen(screens(index).getDefaultConfiguration.getBounds)))
      frames.toList
    }
  }

  def desktopMode = new FrameFactory {
    def create = {
      val bounds = getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration.getBounds
      val frames = Array(new StatusFrame(0, new DesktopMode(bounds)))
      frames.toList
    }
  }
}
