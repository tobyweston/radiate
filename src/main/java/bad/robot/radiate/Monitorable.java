package bad.robot.radiate;

@Deprecated
public interface Monitorable {

    Status getStatus();
    Activity getActivity();
    Progress getProgress();
}
