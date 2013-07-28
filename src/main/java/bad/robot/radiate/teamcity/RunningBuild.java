package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;

class RunningBuild extends Build {

    @SerializedName("running-info")
    private final RunInformation runInformation;

    public RunningBuild(String id, String number, String href, String status, String statusText, String startDate, String finishDate, BuildType buildType, RunInformation runInformation) {
        super(id, number, href, status, statusText, startDate, finishDate, buildType);
        this.runInformation = runInformation;
    }

    public RunInformation getRunInformation() {
        return runInformation;
    }
}
