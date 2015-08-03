package bad.robot

import java.net.URL

import org.scalamock.matchers.Matcher

object UrlMatcher {

  def urlContainingPath(path: String) = new UrlMatcher(path)

  private def getFullPathFrom(url: URL): String = {
    def queryExists(url: URL) = url.getQuery != null
    def portExists(url: URL) = url.getPort() != -1

    val builder = new StringBuilder(url.getProtocol).append("://").append(url.getHost)
    if (portExists(url))
      builder.append(":").append(url.getPort)
    builder.append(url.getPath)
    if (queryExists(url))
      builder.append("?").append(url.getQuery).toString()
    builder.toString()
  }

}

class UrlMatcher(path: String) extends Matcher[URL] {
  def safeEquals(url: URL): Boolean = {
    UrlMatcher.getFullPathFrom(url).contains(path)
  }
}