package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

public class BuildTypes extends TeamCityObject {

    @SerializedName("buildType")
    private final Collection<BuildType> buildTypes;

    public BuildTypes(BuildType... buildTypes) {
        this.buildTypes = new ArrayList<BuildType>(asList(buildTypes));
    }

}
