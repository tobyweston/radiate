package bad.robot.radiate.teamcity;

public class Username extends bad.robot.http.configuration.AbstractValueType<String> {

    public static Username username(String username) {
        if (username == null)
            return new NoUsername();
        return new Username(username);
    }

    protected Username(String value) {
        super(value);
    }
}
