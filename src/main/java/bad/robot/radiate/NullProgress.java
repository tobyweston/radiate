package bad.robot.radiate;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Deprecated
public class NullProgress extends Progress {

    public NullProgress() {
        super(0, 0);
    }

    @Override
    public void increment() { }

    @Override
    public void decrement() { }

    @Override
    public Progress add(Progress progress) {
        return super.add(progress);
    }

    @Override
    public int asAngle() {
        return 0;
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
    public int numberOfBuilds() {
        return 0;
    }

    @Override
    public String toString() {
        return "No progress";
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
