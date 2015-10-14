package bad.robot.radiate.ui

import java.beans.PropertyChangeEvent

class AlphaTransparencyChangeEvent(action: AnyRef, oldValue: Float, newValue: Float) extends PropertyChangeEvent(action, "fade", oldValue, newValue)
