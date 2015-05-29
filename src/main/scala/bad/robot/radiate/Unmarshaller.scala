package bad.robot.radiate

trait UnmarshallerS[F, T] {
  def unmarshall(raw: F): T
}
