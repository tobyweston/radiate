package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;
import bad.robot.radiate.Url;

import java.net.URL;

import static bad.robot.radiate.teamcity.TeamCityEndpoint.buildsEndpoint;
import static java.lang.String.format;

public class Server {

    private final String host;
    private final Integer port;

    public Server(String host, Integer port) {
        this.host = host.replace("http://", "");
        this.port = port;
    }

    public URL urlFor(Hypermedia endpoint) {
        return Url.url(baseUrl() + endpoint.getHref());
    }

    public URL urlFor(BuildLocatorBuilder locator) {
        return Url.url(baseUrl() + buildsEndpoint.getHref() + locator.build());
    }

    private String baseUrl() {
        return format("http://%s:%d", host, port);
    }

}
