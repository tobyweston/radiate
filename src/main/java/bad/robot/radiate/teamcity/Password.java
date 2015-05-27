package bad.robot.radiate.teamcity;

@Deprecated
public class Password extends bad.robot.http.configuration.AbstractValueType<String> {

    public static Password password(String password) {
        if (password == null)
            return new NoPassword();
        return new Password(password);
    }

    protected Password(String value) {
        super(value);
    }

    public bad.robot.http.configuration.Password asSimpleHttp() {
        return bad.robot.http.configuration.Password.password(value);
    }
}
