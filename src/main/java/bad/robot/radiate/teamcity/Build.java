package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

public class Build extends TeamCityObject implements Hypermedia {

    private final String id;
    private final String number;
    private final String href;
    private final String statusText;
    private final String startDate;
    private final String finishDate;
    private final BuildType buildType;

    public Build(String id, String number, String href, String statusText, String startDate, String finishDate, BuildType buildType) {
        this.id = id;
        this.number = number;
        this.href = href;
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
