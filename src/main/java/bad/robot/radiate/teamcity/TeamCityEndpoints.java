package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

@Deprecated
public class TeamCityEndpoints {

    public static Hypermedia projectsEndpointFor(final Authorisation authorisation) {
        return () -> String.format("/%s/app/rest/projects", authorisation);
    }

    public static Hypermedia buildsEndpointFor(final Authorisation authorisation) {
        return () -> String.format("/%s/app/rest/builds/", authorisation);
    }
}
