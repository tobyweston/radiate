package bad.robot.radiate;

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

    public void decrement() {
        current--;
    }

    public Progress add(Progress progress) {
        return new Progress(current + progress.current, (int) (max + progress.max));
    }

    public int current() {
        return current;
    }

    public int asAngle() {
        return - asPercentageOf(360).intValue();
    }

    private int asPercentage() {
        return asPercentageOf(100).intValue();
    }

    private Double asPercentageOf(int i) {
        return current / max * i;
    }

    @Override
    public String toString() {
        return asPercentage() + "%";
    }

    public boolean complete() {
        return current >= max;
    }

    public boolean lessThan(int amount) {
        return current < amount;
    }

    public boolean greaterThan(int amount) {
        return current > amount;
    }

    public boolean lessThan(Progress progress) {
        return current < progress.current;
    }

    public boolean greaterThan(Progress progress) {
        return current > progress.current;
    }
}
