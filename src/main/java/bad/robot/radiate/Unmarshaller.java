package bad.robot.radiate;

public interface Unmarshaller<F, T> {

    T unmarshall(F raw);
}
