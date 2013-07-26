package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

enum TeamCityEndpoint implements Hypermedia {

    projectsEndpoint() {
        @Override
        public String getHref() {
            return "/guestAuth/app/rest/projects";
        }
    },
    buildsEndpint() {
        @Override
        public String getHref() {
            return "/guestAuth/app/rest/builds/";
        }
    };
}
