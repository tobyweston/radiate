package bad.robot.radiate;

import static java.lang.String.format;

public class Environment {

    public static String getEnvironmentVariable(String variable) {
        if (System.getenv(variable) == null)
            throw new IllegalArgumentException(format("Please set environment variable '%s'", variable));
        return System.getenv(variable);
    }

    public static String getEnvironmentVariable(String variable, String defaultValue) {
        if (System.getenv(variable) == null)
            return defaultValue;
        return System.getenv(variable);
    }
}
