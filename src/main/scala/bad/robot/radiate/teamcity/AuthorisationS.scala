package bad.robot.radiate.teamcity

sealed trait AuthorisationS {
  val pathSegment: String
  override def toString = pathSegment
}

object AuthorisationS {
  def authorisationFor(username: UsernameS, password: PasswordS): AuthorisationS = {
    if ((username == NoUsernameS) || (password == NoPasswordS))
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