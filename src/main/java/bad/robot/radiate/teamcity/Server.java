package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;
import bad.robot.radiate.Url;

import java.net.URL;

import static java.lang.String.format;

public class Server {

    public static final int defaultPort = 8111;

    private final String host;
    private final Integer port;

    public Server(String host) {
        this(host, defaultPort);
    }

    public Server(String host, Integer port) {
        validate(host, port);
        this.host = host;
        this.port = port;
    }

    public URL urlFor(Hypermedia endpoint) {
        return Url.url(url() + endpoint.getHref());
    }

    public URL urlFor(Hypermedia endpoint, BuildLocatorBuilder locator) {
        return Url.url(url() + endpoint.getHref() + locator.build());
    }

    private static void validate(String host, Integer port) {
        if (host == null || port == null)
            throw new IllegalArgumentException("please supply a host name and port");
        if (host.contains("http"))
            throw new IllegalArgumentException("no need to specify a protocol, just give me a hostname");
    }

    private String url() {
        return format("http://%s:%d/", host, port);
    }
}
