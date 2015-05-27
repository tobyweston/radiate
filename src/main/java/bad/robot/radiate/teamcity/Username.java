package bad.robot.radiate.teamcity;

@Deprecated
public class Username extends bad.robot.http.configuration.AbstractValueType<String> {

    public static Username username(String username) {
        if (username == null)
            return new NoUsername();
        return new Username(username);
    }

    protected Username(String value) {
        super(value);
    }

    bad.robot.http.configuration.Username asSimpleHttp() {
        return bad.robot.http.configuration.Username.username(value);
    }

}
