package bad.robot.radiate.teamcity;

import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static java.lang.Integer.valueOf;

public class BootstrapServer extends Server {

    public BootstrapServer() {
        super(getEnvironmentVariable("teamcity.host"), valueOf(getEnvironmentVariable("teamcity.port", "8111")));
    }
}
