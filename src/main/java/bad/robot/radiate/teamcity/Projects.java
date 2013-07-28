package bad.robot.radiate.teamcity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class Projects extends TeamCityObject implements Iterable<Project> {

    @SerializedName("project")
    private Collection<Project> projects = new ArrayList<Project>();

    Projects(Collection<Project> projects) {
        this.projects = projects;
    }

    @Override
    public Iterator<Project> iterator() {
        return projects.iterator();
    }
}
