package bad.robot.radiate.teamcity;

public interface Monitor {

    Iterable<Project> retrieveProjects();

    Iterable<BuildType> retrieveBuildTypes(Iterable<Project> projects);

    Build retrieveLatestBuild(BuildType buildType);
}
