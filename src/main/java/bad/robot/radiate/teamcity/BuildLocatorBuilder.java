package bad.robot.radiate.teamcity;

@Deprecated
class BuildLocatorBuilder {

    private final StringBuilder locator = new StringBuilder();

    public static BuildLocatorBuilder latest(BuildType buildType) {
        return new BuildLocatorBuilder().with(buildType);
    }

    public static BuildLocatorBuilder running(BuildType buildType) {
        return new BuildLocatorBuilder().with(buildType).running();
    }

    BuildLocatorBuilder with(BuildType type) {
        withSeparator().append("buildType:").append(type.getId());
        return this;
    }

    BuildLocatorBuilder running() {
        withSeparator().append("running:true");
        return this;
    }

    private StringBuilder withSeparator() {
        if (locator.length() > 0)
            locator.append(",");
        return locator;
    }

    String build() {
        return locator.toString();
    }
}
