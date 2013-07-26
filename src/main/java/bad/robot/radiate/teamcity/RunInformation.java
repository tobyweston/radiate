package bad.robot.radiate.teamcity;

public class RunInformation extends TeamCityObject {

    private final Integer percentageComplete;
    private final Integer elapsedSeconds;
    private final Integer estimatedTotalSeconds;

    public RunInformation(Integer percentageComplete, Integer elapsedSeconds, Integer estimatedTotalSeconds) {
        this.percentageComplete = percentageComplete;
        this.elapsedSeconds = elapsedSeconds;
        this.estimatedTotalSeconds = estimatedTotalSeconds;
    }
}
