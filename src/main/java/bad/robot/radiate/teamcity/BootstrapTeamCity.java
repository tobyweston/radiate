package bad.robot.radiate.teamcity;

import static bad.robot.http.HttpClients.anApacheClient;

class BootstrapTeamCity extends TeamCity {

    public BootstrapTeamCity() {
        super(new BootstrapServer(), anApacheClient(), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }
}
