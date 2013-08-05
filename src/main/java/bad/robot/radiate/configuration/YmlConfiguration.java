package bad.robot.radiate.configuration;

import static bad.robot.radiate.Environment.getEnvironmentVariable;

public class YmlConfiguration implements Configuration {

    public YmlConfiguration() {
    }

    @Override
    public String host() {
        return getEnvironmentVariable("teamcity.host");
    }

    @Override
    public Integer port() {
        return Integer.valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }
}
