package bad.robot.radiate.teamcity;

public interface TeamCityConfiguration {

    String host();
    Integer port();
    Iterable<Project> filter(Iterable<Project> projects);
}
