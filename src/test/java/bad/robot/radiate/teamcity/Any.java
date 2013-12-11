package bad.robot.radiate.teamcity;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Any {

    private static final AtomicInteger number = new AtomicInteger(0);

    public static Projects projects() {
        return new Projects(new ArrayList() {{
            add(new FullProject(anyId(), anyName(), anyHref(), buildTypes()));
            add(new FullProject(anyId(), anyName(), anyHref(), buildTypes()));
        }});
    }

    public static Project project(BuildTypes buildTypes) {
        return new FullProject(anyId(), anyName(), anyHref(), buildTypes);
    }

    public static BuildTypes buildTypes() {
        return new BuildTypes(buildType(), buildType(), buildType());
    }

    public static BuildType buildType() {
        return new BuildType(anyId(), anyName(), anyHref(), "project:" + anyString(8), "pid:" + anyString(5));
    }

    public static Build build() {
        return new Build(anyId(), incrementingNumber(), anyHref(), "SUCCESS", "Success", anyString(10), anyString(10), buildType());
    }

    public static Build runningBuild() {
        RunInformation runInformation = new RunInformation(74, 12, 23, false, false);
        return new RunningBuild(anyId(), incrementingNumber(), anyHref(), "SUCCESS", "Success", anyString(10), anyString(10), buildType(), runInformation);
    }

    public static Build runningBuildPercentageCompleteAt(int percentageComplete) {
        RunInformation runInformation = new RunInformation(percentageComplete, 12, 23, false, false);
        return new RunningBuild(anyId(), incrementingNumber(), anyHref(), "SUCCESS", "Success", anyString(10), anyString(10), buildType(), runInformation);
    }

    private static String incrementingNumber() {
        return Integer.toString(number.incrementAndGet());
    }

    private static String anyHref() {
        return "/href:" + anyString(12);
    }

    private static String anyName() {
        return "name:" + anyString(11);
    }

    private static String anyId() {
        return "id:" + anyString(8);
    }

    private static String anyString(int length) {
        return RandomStringUtils.random(length, true, false);
    }
}
