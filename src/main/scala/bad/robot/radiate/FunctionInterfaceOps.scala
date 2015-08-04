package bad.robot.radiate

import java.util.concurrent.Callable
import java.util.function.{Supplier, Consumer}

import bad.robot.http.MessageContent

object FunctionInterfaceOps {

  implicit def toConsumer[A](function: A => Unit): Consumer[A] = {
    new Consumer[A]() {
      override def accept(arg: A): Unit = function.apply(arg)
    }
  }

  implicit def toSupplier[A](function: => A): Supplier[A] = {
    new Supplier[A]() {
      override def get(): A = function
    }
  }

  implicit def toRunnable(function: => Unit): Runnable = {
    new Runnable {
      override def run(): Unit = function
    }
  }

  implicit def toCallable[A](function: => A): Callable[A] = {
    new Callable[A] {
      override def call(): A = function
    }
  }

  implicit def toHypermedia(value: String): Hypermedia = {
    new Hypermedia {
      override def href: String = value
    }
  }

  implicit def toMessageContent(content: String): MessageContent = {
    new MessageContent {
      override def asString() = content
    }
  }

}

