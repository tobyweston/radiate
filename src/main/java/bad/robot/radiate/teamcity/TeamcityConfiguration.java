package bad.robot.radiate.teamcity;

public interface TeamCityConfiguration {

    String host();
    Integer port();
    Iterable<Project> projects(TeamCity teamcity);

}
