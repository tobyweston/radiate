package bad.robot.radiate.teamcity;

public interface TeamcityConfiguration {

    String host();
    Integer port();
    Iterable<Project> projects(TeamCity teamcity);

}
