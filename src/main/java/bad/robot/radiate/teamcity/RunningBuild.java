package bad.robot.radiate.teamcity;

import bad.robot.radiate.Activity;
import com.google.gson.annotations.SerializedName;

import static bad.robot.radiate.Activity.Progressing;

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

    @Override
    public Activity getActivity() {
        return Progressing;
    }

}
