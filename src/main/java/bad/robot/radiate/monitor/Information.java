package bad.robot.radiate.monitor;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class Information {

    private final String information;

    public Information(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object that) {
        return reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return information;
    }
}
