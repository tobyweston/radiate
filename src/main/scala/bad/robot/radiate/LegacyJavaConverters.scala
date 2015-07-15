package bad.robot.radiate

import bad.robot.radiate.{Activity => LegacyActivity}
import bad.robot.radiate.{Progress => LegacyProgress}
import bad.robot.radiate.teamcity.{Authorisation => LegacyAuthorisation}

import bad.robot.radiate.teamcity.{GuestAuthorisationS, BasicAuthorisationS, AuthorisationS, Authorisation}

object LegacyJavaConverters {

  implicit def toActivity(scala: ActivityS): LegacyActivity = {
    scala match {
      case Busy => LegacyActivity.Busy
      case Error => LegacyActivity.Error
      case Idle => LegacyActivity.Idle
      case Progressing => LegacyActivity.Progressing
      case _ => throw new UnsupportedOperationException
    }
  }

  implicit def toProgress(scala: ProgressS): LegacyProgress = {
    new LegacyProgress(scala.asAngle, 360)
  }

  implicit def toAuthorisation(scala: AuthorisationS): LegacyAuthorisation = {
    scala match {
      case BasicAuthorisationS => LegacyAuthorisation.BasicAuthorisation
      case GuestAuthorisationS => LegacyAuthorisation.GuestAuthorisation
    }
  }
}
