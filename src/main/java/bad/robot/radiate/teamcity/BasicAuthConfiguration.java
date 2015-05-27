package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.configuration.BasicAuthCredentials;

@Deprecated
class BasicAuthConfiguration {

    private final Server server;
    private final Username username;
    private final Password password;

    public static BasicAuthConfiguration basicAuthConfiguration(TeamCityConfiguration configuration) {
        Username username = configuration.username();
        Password password = configuration.password();
        if (username.equals(new NoUsername()) || password.equals(new NoPassword()))
            return new GuestAuthentication();

        String host = configuration.host();
        Integer port = configuration.port();
        Server server = new Server(host, port);
        return new BasicAuthConfiguration(server, username, password);
    }

    protected BasicAuthConfiguration(Server server, Username username, Password password) {
        this.server = server;
        this.username = username;
        this.password = password;
    }

    public void applyTo(CommonHttpClient client) {
        client.with(BasicAuthCredentials.basicAuth(username.asSimpleHttp(), password.asSimpleHttp(), server.urlFor(() -> "/")));
    }

}
