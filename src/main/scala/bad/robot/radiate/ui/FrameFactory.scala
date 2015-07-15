package bad.robot.radiate.ui

import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment

trait FrameFactoryS {
  def create: List[StatusFrameScala]
}

object FrameFactoryS {

  def fullScreen = new FrameFactoryS {
    def create = {
      val screens = getLocalGraphicsEnvironment.getScreenDevices
      val frames = (0 until screens.length).map(index => new StatusFrameScala(index, new FullScreenS(screens(index).getDefaultConfiguration.getBounds)))
      frames.toList
    }
  }

  def desktopMode = new FrameFactoryS {
    def create = {
      val bounds = getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration.getBounds
      val frames = Array(new StatusFrameScala(0, new DesktopModeS(bounds)))
      frames.toList
    }
  }
}
