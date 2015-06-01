package bad.robot.radiate.ui

import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment

trait FrameFactoryS {
  def create: List[StatusFrameS]
}

object FrameFactoryS {

  def fullScreen = new FrameFactoryS {
    def create = {
      val screens = getLocalGraphicsEnvironment.getScreenDevices
      val frames = (0 until screens.length).map(index => new StatusFrameS(index, new FullScreen(screens(index).getDefaultConfiguration.getBounds)))
      frames.toList
    }
  }

  def desktopMode = new FrameFactoryS {
    def create = {
      val bounds = getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration.getBounds
      val frames = Array(new StatusFrameS(0, new DesktopMode(bounds)))
      frames.toList
    }
  }
}
