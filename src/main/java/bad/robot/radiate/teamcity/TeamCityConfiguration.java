package bad.robot.radiate.teamcity;

@Deprecated
public interface TeamCityConfiguration {
    String host();
    Integer port();
    Iterable<Project> filter(Iterable<Project> projects);
    Username username();
    Password password();
    Authorisation authorisation();
}
