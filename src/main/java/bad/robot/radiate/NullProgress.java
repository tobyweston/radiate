package bad.robot.radiate;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class NullProgress extends Progress {

    private final String id = "4de821a0-58cf-11e3-949a-0800200c9a66";

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
    public boolean lessThan(Progress progress) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean greaterThan(Progress progress) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int over() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object that) {
        return reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
