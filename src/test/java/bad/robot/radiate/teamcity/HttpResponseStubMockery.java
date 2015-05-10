package bad.robot.radiate.teamcity;

import bad.robot.http.HeaderPair;
import bad.robot.http.HttpResponse;
import bad.robot.http.MessageContent;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import static bad.robot.http.HeaderList.headers;

public class HttpResponseStubMockery extends JUnitRuleMockery {

    public HttpResponse stubResponseReturning(final String content) {
        final HttpResponse response = this.mock(HttpResponse.class);
        checking(new Expectations() {{
            allowing(response).getContent(); will(returnValue((MessageContent) () -> content));
            allowing(response).getHeaders(); will(returnValue(headers(HeaderPair.header("content-type", "application/json"))));
        }});
        return response;
    }
}
