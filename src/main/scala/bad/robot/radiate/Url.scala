package bad.robot.radiate

import java.net.URL

object Url {
  def url(url: String): URL = {
    new URL(url.replace(" ", "%20"))
  }
}
