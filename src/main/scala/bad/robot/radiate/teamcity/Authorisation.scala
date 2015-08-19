package bad.robot.radiate.teamcity

import bad.robot.radiate.config.{NoPassword, Password}

sealed trait Authorisation {
  val pathSegment: String
  override def toString = pathSegment
}

object Authorisation {
  def authorisationFor(username: Username, password: Password): Authorisation = {
    if ((username == NoUsername) || (password == NoPassword))
      GuestAuthorisationS
    else
      BasicAuthorisationS
  }
}

case object GuestAuthorisationS extends Authorisation {
  override val pathSegment = "guestAuth"
}

case object BasicAuthorisationS extends Authorisation {
  override val pathSegment = "httpAuth"
}