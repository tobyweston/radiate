package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.HttpResponse

class UnexpectedResponseS(url: URL, response: HttpResponse) extends TeamCityException(s"Unexpected HTTP response from $url (${response.getStatusCode}, ${response.getStatusMessage})")