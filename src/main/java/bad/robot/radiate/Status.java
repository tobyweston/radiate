package bad.robot.radiate;

/**
 * <ul><li>{@link Status#Ok} - A positive build state; for example, all tests passing.</li></ul>
 * <ul><li>{@link Status#Broken} - A negative build state; for example, tests breaking.</li></ul>
 * <ul><li>{@link Status#Unknown} - Unknown build state.
 */
public enum Status {

    Ok,
    Broken,
    Unknown;

}
