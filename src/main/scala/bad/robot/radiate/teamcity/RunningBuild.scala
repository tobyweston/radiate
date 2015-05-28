package bad.robot.radiate.teamcity

import bad.robot.radiate.{ProgressS, Progressing}
import com.google.gson.annotations.SerializedName

class RunningBuildS(id: String, number: String, href: String, status: String, statusText: String, startDate: String, finishDate: String, buildType: BuildTypeScala, @SerializedName("running-info") private val _runInformation: RunInformation) extends BuildS(id, number, href, status, statusText, startDate, finishDate, buildType) {

  def runInformation = _runInformation

  override def activity = Progressing

  override def progress = new ProgressS(runInformation.getPercentageComplete, 100)
}
