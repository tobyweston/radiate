package bad.robot.radiate.teamcity;

class BootstrapTeamCity extends TeamCity {

    public BootstrapTeamCity() {
        super(new BootstrapServer(), new HttpClientFactory().create(), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

}
