package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.http.MessageContent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(JMock.class)
public class JsonProjectsUnmarshallerTest {

    private final Mockery context = new JUnit4Mockery();
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
        Iterable<Project> projects = unmarshaller.unmarshall(stubResponseReturning(json));
        assertThat(projects, containsInAnyOrder(
            new Project("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root"),
            new Project("simple_excel", "simple-excel", "/guestAuth/app/rest/projects/id:simple_excel")
        ));
    }

    @Test
    public void unmarshallEmptyHttpResponseWithoutShittingItself() {
        unmarshaller.unmarshall(stubResponseReturning(""));
    }

    private HttpResponse stubResponseReturning(final String content) {
        final HttpResponse response = context.mock(HttpResponse.class);
        context.checking(new Expectations() {{
            allowing(response).getContent(); will(returnValue(new MessageContent() {
                @Override
                public String asString() {
                    return content;
                }
            }));
        }});
        return response;
    }

}
