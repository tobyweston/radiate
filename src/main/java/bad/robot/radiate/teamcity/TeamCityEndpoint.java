package bad.robot.radiate.teamcity;

enum TeamCityEndpoint {

    projectsEndpoint("/guestAuth/app/rest/projects");

    private String endpoint;

    TeamCityEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}

