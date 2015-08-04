package bad.robot.radiate.ui

import org.specs2.mutable.Specification

class FadeOutTest extends Specification {

  val fadeCount = 10

  "Sets the alpha transparency" >> {
    val listener = new PropertyChangeListenerStub
    val fade = new FadeOut

    for (_ <- 0 until fadeCount) {
      fade.fireEvent(List(listener))
    }

    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=1.0; newValue=0.9; propagationId=null; source=0.90]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.9; newValue=0.8; propagationId=null; source=0.80]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.8; newValue=0.7; propagationId=null; source=0.70]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.7; newValue=0.6; propagationId=null; source=0.60]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.6; newValue=0.5; propagationId=null; source=0.50]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.5; newValue=0.4; propagationId=null; source=0.40]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.4; newValue=0.3; propagationId=null; source=0.30]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.3; newValue=0.2; propagationId=null; source=0.20]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.2; newValue=0.1; propagationId=null; source=0.10]") must_== true
    listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.1; newValue=0.0; propagationId=null; source=0.00]") must_== true
  }

  s"Event does not fire (after it's fired $fadeCount times)" >> {
    val listener = new PropertyChangeListenerStub
    val fade = new FadeOut

    for (_ <- 0 to fadeCount) {
      fade.fireEvent(List(listener))
    }
    listener.size must_== fadeCount
  }

  s"Event is done after $fadeCount times" >> {
    val listener = new PropertyChangeListenerStub
    val fade = new FadeOut

    for (_ <- 0 until fadeCount -1) {
      fade.fireEvent(List(listener))
    }
    fade.done must_== false
    fade.fireEvent(List(listener))
    fade.done must_== true
  }
}
