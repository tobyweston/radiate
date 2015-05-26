package bad.robot.radiate.teamcity

import bad.robot.radiate.HypermediaS

case class BuildTypeScala(id: String, name: String, href: String, projectName: String, projectId: String) extends TeamCityObjectS with HypermediaS
