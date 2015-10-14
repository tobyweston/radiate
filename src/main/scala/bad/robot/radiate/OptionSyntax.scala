package bad.robot.radiate

object OptionSyntax {
  def NonEmptyOption(value: String) = Option(value).filter(_.trim.nonEmpty)
  def NonEmptyOption(value: Option[String]) = value.filter(_.trim.nonEmpty)
}
