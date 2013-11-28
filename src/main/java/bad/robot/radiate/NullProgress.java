package bad.robot.radiate;

public class NullProgress extends Progress {
    public NullProgress() {
        super(0, 0);
    }

    @Override
    public void increment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void decrement() {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean complete() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean lessThan(int amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean greaterThan(int amount) {
        throw new UnsupportedOperationException();
    }
}
