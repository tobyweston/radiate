package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static java.util.Arrays.asList;

class BuildTypes extends TeamCityObject implements Iterable<BuildType> {

    @SerializedName("buildType")
    private final Collection<BuildType> buildTypes;

    public BuildTypes(BuildType... buildTypes) {
        this.buildTypes = new ArrayList<BuildType>(asList(buildTypes));
    }

    @Override
    public Iterator<BuildType> iterator() {
        return buildTypes.iterator();
    }
}
