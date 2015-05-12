package bad.robot.radiate

import org.scalamock.matchers.ArgThat

import scala.reflect.ClassTag

package object scalamock {

  def anyTypedOf[T, U: ClassTag]: ArgThat[T] = new ArgThat[T]({
    case x: U => true
    case _ => false
  })

}
