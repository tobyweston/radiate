package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;
import com.google.gson.annotations.SerializedName;

class Project extends TeamCityObject implements Hypermedia {

    private final String id;
    private final String name;
    private final String href;

    @SerializedName("buildTypes")
    private final BuildTypes buildTypes;

    public Project(String id, String name, String href, BuildTypes buildTypes) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.buildTypes = buildTypes;
    }

    public Project(String id, String name, String href) {
        this(id, name, href, null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getHref() {
        return href;
    }

    BuildTypes getBuildTypes() {
        return buildTypes;
    }

}
