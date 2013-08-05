package bad.robot.radiate.teamcity;

import bad.robot.http.Header;
import bad.robot.http.HttpResponse;
import com.googlecode.totallylazy.Predicate;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

class UnexpectedContentType extends TeamCityException {

    public UnexpectedContentType(HttpResponse response) {
        super(format("Unexpected response format '%s'", sequence(response.getHeaders()).find(new Predicate<Header>() {
            @Override
            public boolean matches(Header header) {
                return header.name().equalsIgnoreCase("content-type");
            }
        }).get().value()));
    }

}
