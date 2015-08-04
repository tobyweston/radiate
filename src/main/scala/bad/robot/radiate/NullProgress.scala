package bad.robot.radiate

class NullProgressS extends ProgressS(0, 0) {

  override def increment() {}

  override def decrement() {}

  override def add(progress: ProgressS) = super.add(progress)

  override def asAngle = 0

  override def complete = throw new UnsupportedOperationException

  override def <(progress: ProgressS) = throw new UnsupportedOperationException

  override def >(progress: ProgressS) = throw new UnsupportedOperationException

  override def numberOfBuilds = 0

  override def toString = "No progress"

}