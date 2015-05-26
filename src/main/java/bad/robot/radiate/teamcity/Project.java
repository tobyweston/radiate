package bad.robot.radiate.teamcity;

import bad.robot.radiate.Hypermedia;

import java.util.Collections;
import java.util.Iterator;

import static java.lang.String.format;

@Deprecated
class Project extends TeamCityObject implements Hypermedia, Iterable<BuildType> {

    private final String id;
    private final String name;
    private final String href;

    public Project(String id, String name, String href) {
        this.id = id;
        this.name = name;
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public Iterator<BuildType> iterator() {
        return Collections.<BuildType>emptyList().iterator();
    }

    @Override
    public String toString() {
        return format("%s (%s)", name, id);
    }
}
