package bad.robot.radiate.teamcity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class TeamCityObject {

    @Override
    public boolean equals(Object that) {
        return reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
