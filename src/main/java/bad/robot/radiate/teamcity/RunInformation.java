package bad.robot.radiate.teamcity;

public class RunInformation extends TeamCityObject {

    private final Integer percentageComplete;
    private final Integer elapsedSeconds;
    private final Integer estimatedTotalSeconds;
    private final Boolean outdated;
    private final Boolean probablyHanging;

    public RunInformation(Integer percentageComplete, Integer elapsedSeconds, Integer estimatedTotalSeconds, Boolean outdated, Boolean probablyHanging) {
        this.percentageComplete = percentageComplete;
        this.elapsedSeconds = elapsedSeconds;
        this.estimatedTotalSeconds = estimatedTotalSeconds;
        this.outdated = outdated;
        this.probablyHanging = probablyHanging;
    }

    public Integer getPercentageComplete() {
        return percentageComplete;
    }

    Integer getElapsedSeconds() {
        return elapsedSeconds;
    }

    Integer getEstimatedTotalSeconds() {
        return estimatedTotalSeconds;
    }

    public Boolean getOutdated() {
        return outdated;
    }

    public Boolean getProbablyHanging() {
        return probablyHanging;
    }
}
