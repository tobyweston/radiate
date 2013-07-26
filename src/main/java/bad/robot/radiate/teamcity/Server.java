package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;
import bad.robot.radiate.Url;

import java.net.URL;

import static java.lang.String.format;

public class Server {

    private final String host;
    private final Integer port;

    public Server(String host, Integer port) {
        if (host == null || port == null)
            throw new IllegalArgumentException("please supply a host name and port");
        if (host.contains("http"))
            throw new IllegalArgumentException("no need to specify a protocol, just give me a hostname");
        this.host = host;
        this.port = port;
    }

    public URL urlFor(Hypermedia endpoint) {
        return Url.url(url() + endpoint.getHref());
    }

    private String url() {
        return format("http://%s:%d/", host, port);
    }
}
