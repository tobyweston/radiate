package bad.robot.radiate.config

import java.time.LocalTime
import java.time.LocalTime._

import scalaz.{Failure, Success, Validation}

object EcoMode {
  def validate(start: Option[String], end: Option[String]): Validation[String, Option[EcoMode]] = (start, end) match {
    case (Time(start), Time(end)) => Success(Some(EcoMode(start, end)))
    case value @ _ => Failure(s"The values for eco mode times must be in the range of '00:00' to '23:59'. Both 'start' and 'end' times must be supplied. The values was $value")
  }
}

case class EcoMode(start: Time, end: Time) {
  def active = now.isAfter(start) || now.isBefore(end)
}

object Time {
  def unapply(value: Option[String]): Option[Time] = {
    val matched = "([01]?[0-9]|2[0-3]):([0-5][0-9])".r
    value.flatMap {
      case matched(hour, minutes) => Some(Time(hour.toInt, minutes.toInt))
      case _ => None
    }
  }

  implicit def toLocalTime(time: Time): LocalTime = LocalTime.of(time.hour, time.minutes)
}

case class Time(hour: Int, minutes: Int)
