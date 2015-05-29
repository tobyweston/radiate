package bad.robot.radiate.teamcity;

@Deprecated
class BootstrapTeamCity extends TeamCity {

    public BootstrapTeamCity() {
        super(new BootstrapServer(), new EnvironmentVariableConfiguration().authorisation(), new HttpClientFactory().create(new EnvironmentVariableConfiguration()), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

}
