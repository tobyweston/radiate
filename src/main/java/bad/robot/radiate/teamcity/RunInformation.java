package bad.robot.radiate.teamcity;

class RunInformation extends TeamCityObject {

    private final Integer percentageComplete;
    private final Integer elapsedSeconds;
    private final Integer estimatedTotalSeconds;

    public RunInformation(Integer percentageComplete, Integer elapsedSeconds, Integer estimatedTotalSeconds) {
        this.percentageComplete = percentageComplete;
        this.elapsedSeconds = elapsedSeconds;
        this.estimatedTotalSeconds = estimatedTotalSeconds;
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
}
