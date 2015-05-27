package bad.robot.radiate.teamcity;

@Deprecated
class NoPassword extends Password {

    public NoPassword() {
        super(null);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NoPassword;
    }

    @Override
    public int hashCode() {
        return 32;
    }

    @Override
    public String toString() {
        return "no password";
    }

}
