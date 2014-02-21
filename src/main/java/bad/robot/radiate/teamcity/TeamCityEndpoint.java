package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

import static bad.robot.radiate.teamcity.Authorisation.*;

enum TeamCityEndpoint implements Hypermedia {

    projectsEndpoint() {
        @Override
        public String getHref() {
            return String.format("/%s/app/rest/projects", GuestAuthorisation);
        }
    },
    buildsEndpoint() {
        @Override
        public String getHref() {
            return String.format("/%s/app/rest/builds/", GuestAuthorisation);
        }
    };
}

