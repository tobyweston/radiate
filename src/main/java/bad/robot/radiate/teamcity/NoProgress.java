package bad.robot.radiate.teamcity;

class NoProgress extends Progress {
    public NoProgress() {
        super(0, 0);
    }

    @Override
    public void increment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Progress add(Progress progress) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int current() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int asAngle() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public boolean complete() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean lessThan(int amount) {
        throw new UnsupportedOperationException();
    }
}
