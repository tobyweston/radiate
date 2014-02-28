package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

public class TeamCityEndpoints {

    public static Hypermedia projectsEndpointFor(final Authorisation authorisation) {
        return new Hypermedia() {
            @Override
            public String getHref() {
                return String.format("/%s/app/rest/projects", authorisation);
            }
        };
    }

    public static Hypermedia buildsEndpointFor(final Authorisation authorisation) {
        return new Hypermedia() {
            @Override
            public String getHref() {
                return String.format("/%s/app/rest/builds/", authorisation);
            }
        };
    }
}
