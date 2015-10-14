package bad.robot.radiate

import scalaz.{-\/, \/-, \/}

object Sequence {
  def sequence[E, A](list: List[E \/ A]): List[E] \/ List[A] = {
    list.partition(_.isLeft) match {
      case (Nil, successes) => \/-(for (\/-(success) <- successes) yield success)
      case (errors, _) => -\/(for (-\/(error) <- errors) yield error)
    }
  }
}