package bad.robot.radiate.teamcity;

import static java.lang.String.format;

public class TeamCityServer {

    private final String host;
    private final Integer port;

    public TeamCityServer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String url() {
        return format("http://%s:%d/", host, port);
    }
}
