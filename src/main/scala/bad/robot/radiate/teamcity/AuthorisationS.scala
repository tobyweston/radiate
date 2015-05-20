package bad.robot.radiate.teamcity

sealed trait AuthorisationS {
  val pathSegment: String
  override def toString = pathSegment
}

object AuthorisationS {
  def authorisationFor(username: Username, password: Password): AuthorisationS = {
    if ((username == new NoUsername) || (password == new NoPassword))
      GuestAuthorisationS
    else
      BasicAuthorisationS
  }
}

case object GuestAuthorisationS extends AuthorisationS {
  override val pathSegment = "guestAuth"
}

case object BasicAuthorisationS extends AuthorisationS {
  override val pathSegment = "httpAuth"
}