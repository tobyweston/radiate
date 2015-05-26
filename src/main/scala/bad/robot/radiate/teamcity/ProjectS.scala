package bad.robot.radiate.teamcity

import bad.robot.radiate.HypermediaS

case class ProjectScala(id: String, name: String, href: String) extends TeamCityObjectS with HypermediaS with Iterable[BuildTypeScala] {

  def iterator = List[BuildTypeScala]().iterator

  override def toString() = s"$name ($id)"
}
