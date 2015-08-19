package bad.robot.radiate

import java.net.URL

object UrlSyntax {

  implicit class UrlOps(url: URL) {
    def withDefaultPort(port: Int): URL = {
      if (url.getPort == -1) new URL(url.getProtocol, url.getHost, port, url.getFile)
      else url
    }
    
    def /(path: String) = {
      val p = if (path.startsWith("/")) path else s"/$path"
      new URL(url.toString + p.replace(" ", "%20"))
    }
  }

  implicit def stringToUrl(url: String): URL = new URL(url)

}


