package bad.robot.radiate

class NullProgress extends Progress(0, 0) {

  override def increment() {}

  override def decrement() {}

  override def add(progress: Progress) = super.add(progress)

  override def asAngle = 0

  override def complete = throw new UnsupportedOperationException

  override def <(progress: Progress) = throw new UnsupportedOperationException

  override def >(progress: Progress) = throw new UnsupportedOperationException

  override def numberOfBuilds = 0

  override def toString = "No progress"

}