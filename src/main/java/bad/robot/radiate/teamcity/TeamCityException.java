package bad.robot.radiate.teamcity;

@Deprecated
public abstract class TeamCityException extends RuntimeException {

    public TeamCityException(String message) {
        super(message);
    }
}
