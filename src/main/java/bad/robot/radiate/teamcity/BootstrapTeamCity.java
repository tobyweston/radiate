package bad.robot.radiate.teamcity;

import static bad.robot.radiate.teamcity.Authorisation.*;

class BootstrapTeamCity extends TeamCity {

    public BootstrapTeamCity() {
        super(new BootstrapServer(), new EnvironmentVariableConfiguration().authorisation(), new HttpClientFactory().create(), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

}
