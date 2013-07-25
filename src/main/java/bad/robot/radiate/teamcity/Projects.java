package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

class Projects implements Iterable<Project> {

    @SerializedName("project")
    private Collection<Project> projects = new ArrayList<Project>();

    @Override
    public Iterator<Project> iterator() {
        return projects.iterator();
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
