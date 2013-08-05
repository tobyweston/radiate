package bad.robot.radiate.teamcity;

import org.junit.Rule;
import org.junit.Test;

import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonBuildUnmarshallerTest {

    @Rule public final HttpResponseStubMockery context = new HttpResponseStubMockery();

    private final JsonBuildUnmarshaller unmarshaller = new JsonBuildUnmarshaller();
    @Test
    public void unmarshalls() {
        Build build = unmarshaller.unmarshall(context.stubResponseReturning(buildJson));
        BuildType buildType = new BuildType("Example_Qa", "QA", "/guestAuth/app/rest/buildTypes/id:Example_Qa", "Example", "Example");
        assertThat(build, is(new Build("465", "159", "/guestAuth/app/rest/builds/id:465", "SUCCESS", "Success", "20130726T150432+0100", "20130726T150541+0100", buildType)));
    }

    @Test
    public void unmarshallsRunningBuild() {
        RunningBuild build = (RunningBuild) unmarshaller.unmarshall(context.stubResponseReturning(runningBuildJson));
        BuildType buildType = new BuildType("Example_Qa", "QA", "/guestAuth/app/rest/buildTypes/id:Example_Qa", "Example", "Example");
        RunInformation runInformation = new RunInformation(9, 7, 85);
        assertThat(build, is(new RunningBuild("465", "159", "/guestAuth/app/rest/builds/id:465", "SUCCESS", "Step 1/1", "20130726T161108+0100", null, buildType, runInformation)));
    }

    private String buildJson = "{" +
            "    \"id\": 465," +
            "    \"number\": \"159\"," +
            "    \"status\": \"SUCCESS\"," +
            "    \"href\": \"/guestAuth/app/rest/builds/id:465\"," +
            "    \"webUrl\": \"http://localhost:8111/viewLog.html?buildId=465&buildTypeId=Example_Qa\"," +
            "    \"personal\": false," +
            "    \"history\": false," +
            "    \"pinned\": false," +
            "    \"statusText\": \"Success\"," +
            "    \"buildType\": {" +
            "        \"id\": \"Example_Qa\"," +
            "        \"name\": \"QA\"," +
            "        \"href\": \"/guestAuth/app/rest/buildTypes/id:Example_Qa\"," +
            "        \"projectName\": \"Example\"," +
            "        \"projectId\": \"Example\"," +
            "        \"webUrl\": \"http://localhost:8111/viewType.html?buildTypeId=Example_Qa\"" +
            "    }," +
            "    \"startDate\": \"20130726T150432+0100\"," +
            "    \"finishDate\": \"20130726T150541+0100\"," +
            "    \"agent\": {" +
            "        \"name\": \"Default Agent\"," +
            "        \"id\": 1," +
            "        \"href\": \"/guestAuth/app/rest/agents/id:1\"" +
            "    }," +
            "    \"tags\": {" +
            "        \"tag\": []" +
            "    }," +
            "    \"properties\": {" +
            "        \"property\": []" +
            "    }," +
            "    \"snapshot-dependencies\": {" +
            "        \"count\": 1," +
            "        \"build\": [" +
            "            {" +
            "                \"id\": 464," +
            "                \"number\": \"167\"," +
            "                \"status\": \"SUCCESS\"," +
            "                \"buildTypeId\": \"Example_Trunk\"," +
            "                \"startDate\": \"20130726T150304+0100\"," +
            "                \"href\": \"/guestAuth/app/rest/builds/id:464\"," +
            "                \"webUrl\": \"http://localhost:8111/viewLog.html?buildId=464&buildTypeId=Example_Trunk\"" +
            "            }" +
            "        ]" +
            "    }," +
            "    \"artifact-dependencies\": {" +
            "        \"count\": 0," +
            "        \"build\": []" +
            "    }," +
            "    \"revisions\": {" +
            "        \"revision\": [" +
            "            {" +
            "                \"version\": \"dea21644bf91ae4d1c119ba60ba736c25d9f4175\"," +
            "                \"vcs-root-instance\": {" +
            "                    \"id\": \"1\"," +
            "                    \"vcs-root-id\": \"Example_ExampleGit\"," +
            "                    \"name\": \"Example Git\"," +
            "                    \"href\": \"/guestAuth/app/rest/vcs-root-instances/id:1\"" +
            "                }" +
            "            }" +
            "        ]" +
            "    }," +
            "    \"triggered\": {" +
            "        \"date\": \"20130726T150432+0100\"" +
            "    }," +
            "    \"changes\": {" +
            "        \"href\": \"/guestAuth/app/rest/changes?build=id:465\"," +
            "        \"count\": 1" +
            "    }," +
            "    \"relatedIssues\": {" +
            "        \"href\": \"/guestAuth/app/rest/builds/id:465/relatedIssues\"" +
            "    }," +
            "    \"artifacts\": {" +
            "        \"href\": \"/guestAuth/app/rest/builds/id:465/artifacts/children\"" +
            "    }" +
            "}";

    private String runningBuildJson = "{" +
            "    \"id\": 465," +
            "    \"number\": \"159\"," +
            "    \"status\": \"SUCCESS\"," +
            "    \"href\": \"/guestAuth/app/rest/builds/id:465\"," +
            "    \"webUrl\": \"http://localhost:8111/viewLog.html?buildId=465&buildTypeId=Example_Trunk\"," +
            "    \"personal\": false," +
            "    \"history\": false," +
            "    \"pinned\": false," +
            "    \"running\": true," +
            "    \"running-info\": {" +
            "        \"percentageComplete\": 9," +
            "        \"elapsedSeconds\": 7," +
            "        \"estimatedTotalSeconds\": 85," +
            "        \"currentStageText\": \"Step 1/1: in directory: /home/fuseuat/example/TeamCity/buildAgent/work/5c96582cd59318d6\"," +
            "        \"outdated\": false," +
            "        \"probablyHanging\": false" +
            "    }," +
            "    \"statusText\": \"Step 1/1\"," +
            "    \"buildType\": {" +
            "        \"id\": \"Example_Qa\"," +
            "        \"name\": \"QA\"," +
            "        \"href\": \"/guestAuth/app/rest/buildTypes/id:Example_Qa\"," +
            "        \"projectName\": \"Example\"," +
            "        \"projectId\": \"Example\"," +
            "        \"webUrl\": \"http://localhost:8111/viewType.html?buildTypeId=Example_Qa\"" +
            "    }," +
            "    \"startDate\": \"20130726T161108+0100\"," +
            "    \"agent\": {" +
            "        \"name\": \"Default Agent\"," +
            "        \"id\": 1," +
            "        \"href\": \"/guestAuth/app/rest/agents/id:1\"" +
            "    }," +
            "    \"tags\": {" +
            "        \"tag\": []" +
            "    }," +
            "    \"properties\": {" +
            "        \"property\": []" +
            "    }," +
            "    \"snapshot-dependencies\": {" +
            "        \"count\": 0," +
            "        \"build\": []" +
            "    }," +
            "    \"artifact-dependencies\": {" +
            "        \"count\": 0," +
            "        \"build\": []" +
            "    }," +
            "    \"revisions\": {" +
            "        \"revision\": [" +
            "            {" +
            "                \"version\": \"5b2e6e3a41e6b08d606ef1d0355262d90d969384\"," +
            "                \"vcs-root-instance\": {" +
            "                    \"id\": \"1\"," +
            "                    \"vcs-root-id\": \"Example_ExampleGit\"," +
            "                    \"name\": \"Example Git\"," +
            "                    \"href\": \"/guestAuth/app/rest/vcs-root-instances/id:1\"" +
            "                }" +
            "            }" +
            "        ]" +
            "    }," +
            "    \"triggered\": {" +
            "        \"date\": \"20130726T150432+0100\"" +
            "    }," +
            "    \"changes\": {" +
            "        \"href\": \"/guestAuth/app/rest/changes?build=id:473\"," +
            "        \"count\": 0" +
            "    }," +
            "    \"relatedIssues\": {" +
            "        \"href\": \"/guestAuth/app/rest/builds/id:473/relatedIssues\"" +
            "    }," +
            "    \"artifacts\": {" +
            "        \"href\": \"/guestAuth/app/rest/builds/id:473/artifacts/children\"" +
            "    }" +
            "}";


}
