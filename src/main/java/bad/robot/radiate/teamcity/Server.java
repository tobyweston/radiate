package bad.robot.radiate.teamcity;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

public class Server {

    private final String host;
    private final Integer port;

    public Server(String host, Integer port) {
        if (host.contains("http"))
            throw new IllegalArgumentException("no need to specify a protocol, just give me a hostname");
        this.host = host;
        this.port = port;
    }

    public URL urlFor(TeamCityEndpoint endpoint) {
        try {
            URL url = new URL(url() + endpoint.toString());
            System.out.println(url);
            return url;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String url() {
        return format("http://%s:%d/", host, port);
    }
}
