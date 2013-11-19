package bad.robot.radiate.ui;

public class Progress {

    private int current;
    private final double max;

    public Progress(int current, int max) {
        this.current = current;
        this.max = max;
    }

    public void increment() {
        current++;
    }

    int asAngle() {
        return - asPercentageOf(360).intValue();
    }

    private Double asPercentageOf(int i) {
        return current / max * i;
    }

    @Override
    public String toString() {
        return asPercentageOf(100).intValue() + "%";
    }

    boolean complete() {
        return current >= max;
    }

    public int current() {
        return current;
    }
}
