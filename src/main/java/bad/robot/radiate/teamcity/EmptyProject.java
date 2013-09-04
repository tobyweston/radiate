package bad.robot.radiate.teamcity;

class EmptyProject extends Project {

    public EmptyProject(String id, String name, String href) {
        super(id, name, href, new BuildTypes());
    }
}
