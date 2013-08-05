package bad.robot.radiate.teamcity;

import java.util.List;

public interface TeamcityConfiguration {

    String host();
    Integer port();
    List<Project> projects();
}
