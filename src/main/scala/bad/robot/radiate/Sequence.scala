package bad.robot.radiate

object Sequence {
  def sequence[E, A](list: List[Either[E, A]]): Either[List[E], List[A]] = {
    list.partition(_.isLeft) match {
      case (Nil, successes) => Right(for (Right(success) <- successes) yield success)
      case (errors, _) => Left(for (Left(error) <- errors) yield error)
    }
  }
}