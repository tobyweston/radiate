package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;

import java.util.Iterator;

@Deprecated
class FullProject extends Project {

    @SerializedName("buildTypes")
    private final BuildTypes buildTypes;

    public FullProject(String id, String name, String href, BuildTypes buildTypes) {
        super(id, name, href);
        this.buildTypes = buildTypes;
    }

    @Override
    public Iterator<BuildType> iterator() {
        return buildTypes.iterator();
    }
}
