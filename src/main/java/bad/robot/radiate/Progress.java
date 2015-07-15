package bad.robot.radiate;

public class Progress {

    private int current;
    private int numberOfBuilds = 1;
    private final double max;

    public Progress(int current, int max) {
        this.current = current;
        this.max = max;
    }

    private Progress(int current, int max, int numberOfBuilds) {
        this(current, max);
        this.numberOfBuilds = numberOfBuilds;
    }

    public void increment() {
        current++;
    }

    public void decrement() {
        current--;
    }

    public Progress add(Progress progress) {
        return new Progress(current + progress.current, (int) (max + progress.max), numberOfBuilds(progress));
    }

    private int numberOfBuilds(Progress progress) {
        return numberOfBuilds() + progress.numberOfBuilds();
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

    public boolean complete() {
        return current >= max;
    }

    public int numberOfBuilds() {
        return numberOfBuilds;
    }

    @Override
    public String toString() {
        return asPercentage() + "%";
    }

    public boolean lessThan(Progress progress) {
        return asPercentage() < progress.asPercentage();
    }

    public boolean greaterThan(Progress progress) {
        return asPercentage() > progress.asPercentage();
    }
}
