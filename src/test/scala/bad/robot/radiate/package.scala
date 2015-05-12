package bad.robot

import org.scalamock.matchers.ArgThat

import scala.reflect.ClassTag

package object radiate {

  def anyTypedOf[T, U: ClassTag]: ArgThat[T] = new ArgThat[T]({
    case x: U => true
    case _ => false
  })
}
