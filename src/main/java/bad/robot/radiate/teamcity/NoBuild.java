package bad.robot.radiate.teamcity;

import bad.robot.radiate.Status;

import static bad.robot.radiate.Status.Unknown;

class NoBuild extends Build {
    public NoBuild() {
        super("", "", "/", "", "", "", "", new BuildType("", "", "/", "", ""));
    }

    @Override
    public Status getStatus() {
        return Unknown;
    }

}
