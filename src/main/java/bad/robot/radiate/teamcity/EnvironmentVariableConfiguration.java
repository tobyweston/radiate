package bad.robot.radiate.teamcity;

import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static bad.robot.radiate.teamcity.Authorisation.*;
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

    @Override
    public Password password() {
        return Password.password(getEnvironmentVariable("TEAMCITY_PASSWORD", null));
    }

    @Override
    public Username username() {
        return Username.username(getEnvironmentVariable("TEAMCITY_USER", null));
    }

    @Override
    public Authorisation authorisation() {
        return Authorisation.authorisationFor(username(), password());
    }
}
