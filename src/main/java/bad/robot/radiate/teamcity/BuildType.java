package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

public class BuildType extends TeamCityObject implements Hypermedia {

    private final String id;
    private final String name;
    private final String href;
    private final String projectName;
    private final String projectId;

    public BuildType(String id, String name, String href, String projectName, String projectId) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.projectName = projectName;
        this.projectId = projectId;
    }

    @Override
    public String getHref() {
        return href;
    }

}
