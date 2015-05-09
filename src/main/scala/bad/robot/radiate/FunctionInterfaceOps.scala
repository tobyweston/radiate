package bad.robot.radiate

import java.util.function.Consumer

object FunctionInterfaceOps {
  implicit def toConsumer[A](function: A => Unit): Consumer[A] = {
    new Consumer[A]() {
      override def accept(arg: A): Unit = function.apply(arg)
    }
  }
}

