package bad.robot.radiate

import org.scalamock.matchers.ArgThat

import scala.reflect.ClassTag

package object scalamock {

  def anyTypedOf[U: ClassTag]: ArgThat[Any] = new ArgThat[Any]({
    case x: U => true
    case _ => false
  })

}
