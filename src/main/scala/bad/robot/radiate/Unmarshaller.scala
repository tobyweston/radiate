package bad.robot.radiate

import bad.robot.radiate.RadiateError.Error

import scalaz.\/

trait Unmarshaller[F, T] {
  def unmarshall(raw: F): Error \/ T
}
