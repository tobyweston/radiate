package bad.robot.radiate.teamcity;

import bad.robot.http.Header;
import bad.robot.http.HttpResponse;
import com.googlecode.totallylazy.Predicate;
import org.apache.commons.lang3.StringUtils;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

@Deprecated
class UnexpectedContentType extends TeamCityException {

    public UnexpectedContentType(HttpResponse response) {
        super(format("Unexpected response format '%s' (%s %s) from %s%n%s", sequence(response.getHeaders()).find(contentType()).get().value(), response.getStatusCode(), response.getStatusMessage(), response.getOriginatingUri(), abbreviated(response)));
    }

    private static String abbreviated(HttpResponse response) {
        String body = response.getContent().asString();
        return StringUtils.abbreviate(body, 400);
    }

    private static Predicate<Header> contentType() {
        return header -> header.name().equalsIgnoreCase("content-type");
    }

}
