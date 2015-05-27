package bad.robot.radiate

import bad.robot.radiate.teamcity.{GuestAuthorisationS, BasicAuthorisationS, AuthorisationS, Authorisation}

object LegacyJavaConverters {

  implicit def toActivity(scala: ActivityS): Activity = {
    scala match {
      case Busy => Activity.Busy
      case Error => Activity.Error
      case Idle => Activity.Idle
      case Progressing => Activity.Progressing
      case _ => throw new UnsupportedOperationException
    }
  }

  implicit def toProgress(scala: ProgressS): Progress = {
    new Progress(scala.asAngle, 360)
  }

  implicit def toAuthorisation(scala: AuthorisationS): Authorisation = {
    scala match {
      case BasicAuthorisationS => Authorisation.BasicAuthorisation
      case GuestAuthorisationS => Authorisation.GuestAuthorisation
    }
  }
}
