package bad.robot.radiate.teamcity;

import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static java.lang.Integer.valueOf;

public class EnvironmentVariableConfiguration implements TeamcityConfiguration {

    @Override
    public String host() {
        return getEnvironmentVariable("teamcity.host");
    }

    @Override
    public Integer port() {
        return valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }

    @Override
    public Iterable<Project> projects(TeamCity teamcity) {
        return teamcity.retrieveProjects();
    }
}
