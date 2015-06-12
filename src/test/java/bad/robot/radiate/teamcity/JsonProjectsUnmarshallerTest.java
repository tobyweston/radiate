package bad.robot.radiate.teamcity;

import org.junit.Rule;
import org.junit.Test;

import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class JsonProjectsUnmarshallerTest {

    @Rule public final HttpResponseStubMockery context = new HttpResponseStubMockery();

    private final JsonProjectsUnmarshaller unmarshaller = new JsonProjectsUnmarshaller();

    @Test
    public void unmarshallHttpResponse() {
        String json = "{" +
                "    \"project\": [" +
                "        {" +
                "            \"id\": \"_Root\"," +
                "            \"name\": \"<Root project>\"," +
                "            \"href\": \"/guestAuth/app/rest/projects/id:_Root\"" +
                "        }," +
                "        {" +
                "            \"id\": \"simple_excel\"," +
                "            \"name\": \"simple-excel\"," +
                "            \"href\": \"/guestAuth/app/rest/projects/id:simple_excel\"" +
                "        }" +
                "    ]" +
                "}";
        Iterable<Project> projects = unmarshaller.unmarshall(context.stubResponseReturning(json));
        assertThat(projects, containsInAnyOrder(
            new Project("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root"),
            new Project("simple_excel", "simple-excel", "/guestAuth/app/rest/projects/id:simple_excel")
        ));
    }

    @Test
    public void unmarshallEmptyHttpResponseWithoutShittingItself() {
        Iterable<Project> projects = unmarshaller.unmarshall(context.stubResponseReturning(""));
        assertThat(projects, is(null));
    }

}
