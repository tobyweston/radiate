package bad.robot.radiate.teamcity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class JsonProjectUnmarshallerTest {

    @Rule public final HttpResponseStubMockery context = new HttpResponseStubMockery();

    private final JsonProjectUnmarshaller unmarshaller = new JsonProjectUnmarshaller();

    @Test
    public void unmarshallProject() {
        Project project = unmarshaller.unmarshall(context.stubResponseReturning("{" +
                "    \"id\": \"example\"," +
                "    \"name\": \"example\"," +
                "    \"href\": \"/guestAuth/app/rest/projects/id:example\"," +
                "    \"description\": \"\"," +
                "    \"archived\": false," +
                "    \"webUrl\": \"http://localhost:8111/project.html?projectId=example\"," +
                "    \"parentProject\": {" +
                "        \"id\": \"_Root\"," +
                "        \"name\": \"<Root project>\"," +
                "        \"href\": \"/guestAuth/app/rest/projects/id:_Root\"" +
                "    }," +
                "    \"buildTypes\": {" +
                "        \"buildType\": [" +
                "            {" +
                "                \"id\": \"example_1\"," +
                "                \"name\": \"First\"," +
                "                \"href\": \"/guestAuth/app/rest/buildTypes/id:example_1\"," +
                "                \"projectName\": \"example\"," +
                "                \"projectId\": \"example\"," +
                "                \"webUrl\": \"http://localhost:8111/viewType.html?buildTypeId=example_1\"" +
                "            }," +
                "            {" +
                "                \"id\": \"example_2\"," +
                "                \"name\": \"Second\"," +
                "                \"href\": \"/guestAuth/app/rest/buildTypes/id:example_2\"," +
                "                \"projectName\": \"example\"," +
                "                \"projectId\": \"example\"," +
                "                \"webUrl\": \"http://localhost:8111/viewType.html?buildTypeId=example_2\"" +
                "            }" +
                "        ]" +
                "    }," +
                "    \"templates\": {" +
                "        \"buildType\": []" +
                "    }," +
                "    \"parameters\": {" +
                "        \"property\": []" +
                "    }," +
                "    \"vcsRoots\": {" +
                "        \"href\": \"/guestAuth/app/rest/vcs-roots?locator=project:(id:example)\"" +
                "    }," +
                "    \"projects\": {" +
                "        \"project\": []" +
                "    }" +
                "}"));
        BuildTypes buildTypes = new BuildTypes(
            new BuildType("example_1", "First", "/guestAuth/app/rest/buildTypes/id:example_1", "example", "example"),
            new BuildType("example_2", "Second", "/guestAuth/app/rest/buildTypes/id:example_2", "example", "example")
        );
        assertThat(project, is(new Project("example", "example", "/guestAuth/app/rest/projects/id:example", buildTypes)));
    }

    @Test
    public void unmarshallEmptyProject() {
        Project project = unmarshaller.unmarshall(context.stubResponseReturning("{\n" +
                "    \"id\": \"_Root\",\n" +
                "    \"name\": \"<Root project>\",\n" +
                "    \"href\": \"/guestAuth/app/rest/projects/id:_Root\",\n" +
                "    \"description\": \"Contains all other projects\",\n" +
                "    \"archived\": false,\n" +
                "    \"webUrl\": \"http://example.com:8111/project.html?projectId=_Root\",\n" +
                "    \"buildTypes\": {\n" +
                "        \"buildType\": []\n" +
                "    },\n" +
                "    \"templates\": {\n" +
                "        \"buildType\": []\n" +
                "    },\n" +
                "    \"parameters\": {\n" +
                "        \"property\": []\n" +
                "    },\n" +
                "    \"vcsRoots\": {\n" +
                "        \"href\": \"/guestAuth/app/rest/vcs-roots?locator=project:(id:_Root)\"\n" +
                "    },\n" +
                "    \"projects\": {\n" +
                "        \"project\": [\n" +
                "            {\n" +
                "                \"id\": \"example\",\n" +
                "                \"name\": \"example\",\n" +
                "                \"href\": \"/guestAuth/app/rest/projects/id:example\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}"));
        BuildTypes buildTypes = new BuildTypes();
        assertThat(project, is(new Project("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root", buildTypes)));
    }
}
