package bad.robot.radiate;

@Deprecated
public interface Unmarshaller<F, T> {

    T unmarshall(F raw);
}
