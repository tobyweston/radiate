package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;

import java.net.URL;

import static java.lang.String.format;

@Deprecated
public class UnexpectedResponse extends TeamCityException {

    public UnexpectedResponse(URL url, HttpResponse response) {
        super(format("Unexpected HTTP response from %s (%d, %s)", url, response.getStatusCode(), response.getStatusMessage()));
    }

}
