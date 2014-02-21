package bad.robot.radiate.teamcity;

enum Authorisation {

    GuestAuthorisation("guestAuth"),
    BasicAuthorisation("httpAuth");

    private final String pathSegment;

    Authorisation(String pathSegment) {
        this.pathSegment = pathSegment;
    }

    public String toString() {
        return pathSegment;
    }
}
