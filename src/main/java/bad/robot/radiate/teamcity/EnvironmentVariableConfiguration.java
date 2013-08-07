package bad.robot.radiate.teamcity;

import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static java.lang.Integer.valueOf;

public class EnvironmentVariableConfiguration implements TeamCityConfiguration {

    @Override
    public String host() {
        return getEnvironmentVariable("teamcity.host");
    }

    @Override
    public Integer port() {
        return valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }

    @Override
    public Iterable<Project> filter(Iterable<Project> projects) {
        return projects; // no filtering
    }
}
