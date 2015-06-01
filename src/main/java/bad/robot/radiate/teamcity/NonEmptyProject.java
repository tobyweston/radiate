package bad.robot.radiate.teamcity;

import com.googlecode.totallylazy.Predicate;

@Deprecated
class NonEmptyProject implements Predicate<Project> {

    static NonEmptyProject nonEmpty() {
        return new NonEmptyProject();
    }

    private NonEmptyProject() {
    }

    @Override
    public boolean matches(Project project) {
        return project.iterator().hasNext();
    }
}
