package bad.robot.radiate

import scalaz.\/

trait Unmarshaller[F, T] {
  def unmarshall(raw: F): Error \/ T
}
