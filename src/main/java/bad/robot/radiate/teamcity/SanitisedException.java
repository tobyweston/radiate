package bad.robot.radiate.teamcity;

import static org.apache.commons.lang3.ClassUtils.getShortClassName;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

public class SanitisedException {

    private final Exception underlying;

    public SanitisedException(Exception underlying) {
        this.underlying = underlying;
    }

    public String getMessage() {
        if (getRootCause(underlying) != null)
            return removeClassPreamble(getRootCause(underlying));
        return underlying.getMessage();
    }

    private static String removeClassPreamble(Throwable exception) {
        return getExpandedClassShortName(exception.getClass()) + ": " + defaultString(exception.getMessage());
    }

    public static String getExpandedClassShortName(Class type) {
        String name = getShortClassName(type);
        return join(splitByCharacterTypeCamelCase(name), " ");
    }
}
