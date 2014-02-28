package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.HttpClient;
import bad.robot.http.configuration.Proxy;
import bad.robot.radiate.Url;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.http.configuration.HttpTimeout.httpTimeout;
import static bad.robot.radiate.teamcity.BasicAuthConfiguration.*;
import static com.google.code.tempusfugit.temporal.Duration.minutes;

public class HttpClientFactory {

    public HttpClient create() {
        return anApacheClient().with(httpTimeout(minutes(1)));
    }

    public HttpClient create(TeamCityConfiguration configuration) {
        CommonHttpClient client = anApacheClient();
        basicAuthConfiguration(configuration).applyTo(client);
        return client.with(httpTimeout(minutes(10)));
    }

}
