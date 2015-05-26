package bad.robot.radiate.teamcity;

import bad.robot.radiate.*;

import static bad.robot.radiate.Activity.Idle;
import static bad.robot.radiate.Status.*;

@Deprecated
public class Build extends TeamCityObject implements Hypermedia, Monitorable {

    private final String id;
    private final String number;
    private final String href;
    private final String status;
    private final String statusText;
    private final String startDate;
    private final String finishDate;
    private final BuildType buildType;

    public Build(String id, String number, String href, String success, String statusText, String startDate, String finishDate, BuildType buildType) {
        this.id = id;
        this.number = number;
        this.href = href;
        this.status = success;
        this.statusText = statusText;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.buildType = buildType;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public Status getStatus() {
        if (status.equalsIgnoreCase("SUCCESS"))
            return Ok;
        if (status.equalsIgnoreCase("FAILURE"))
            return Broken;
        return Unknown;
    }

    @Override
    public Activity getActivity() {
        return Idle;
    }

    @Override
    public Progress getProgress() {
        return new NullProgress();
    }

    public String getStatusString() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    @Override
    public String getHref() {
        return href;
    }

}
