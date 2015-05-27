package bad.robot.radiate.teamcity;

@Deprecated
class NoUsername extends Username {

    NoUsername() {
        super(null);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NoUsername;
    }

    @Override
    public int hashCode() {
        return 32;
    }

    @Override
    public String toString() {
        return "no username";
    }
}
