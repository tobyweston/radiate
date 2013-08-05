package bad.robot.radiate.teamcity;

public interface TeamCityConfigurationRenameToAvoidCaseInsensativeOss {

    String host();
    Integer port();
    Iterable<Project> projects(TeamCity teamcity);

}
