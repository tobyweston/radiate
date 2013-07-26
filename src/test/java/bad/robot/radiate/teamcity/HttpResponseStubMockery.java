package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.http.MessageContent;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;

public class HttpResponseStubMockery extends JUnit4Mockery {

    public HttpResponse stubResponseReturning(final String content) {
        final HttpResponse response = this.mock(HttpResponse.class);
        checking(new Expectations() {{
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
