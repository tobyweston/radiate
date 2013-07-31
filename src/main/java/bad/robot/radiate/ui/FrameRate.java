package bad.robot.radiate.ui;

class FrameRate {

    public static final FrameRate videoFramesPerSecond = framesPerSecond(24);

    private final int framesPerSecond;

    static FrameRate
    framesPerSecond(int framesPerSecond) {
        return new FrameRate(framesPerSecond);
    }

    private FrameRate(int framesPerSecond) {
        if (framesPerSecond < 1)
            throw new IllegalArgumentException("frame rates less than 1 seems a little low");
        if (framesPerSecond > 60)
            throw new IllegalArgumentException("frame rates over 60 frames per seconds seems a little high");
        this.framesPerSecond = framesPerSecond;
    }

    int asFrequencyInMillis() {
        return 1000 / framesPerSecond;
    }
}
