package bad.robot.radiate

trait Unmarshaller[F, T] {
  def unmarshall(raw: F): T
}
