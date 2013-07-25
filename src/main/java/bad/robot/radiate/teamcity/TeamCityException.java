package bad.robot.radiate.teamcity;

public abstract class TeamCityException extends RuntimeException {

    public TeamCityException(String message) {
        super(message);
    }
}
