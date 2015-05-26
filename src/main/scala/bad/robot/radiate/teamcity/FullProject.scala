package bad.robot.radiate.teamcity

import com.google.gson.annotations.SerializedName

class FullProjectS(id: String, name: String, href: String, @SerializedName("buildTypes") private val buildTypes: BuildTypesScala) extends ProjectScala(id, name, href) {

  override def iterator = buildTypes.iterator
}