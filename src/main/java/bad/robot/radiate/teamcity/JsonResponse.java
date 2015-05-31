package bad.robot.radiate.teamcity;

import bad.robot.http.Header;
import bad.robot.http.HttpResponse;
import com.googlecode.totallylazy.Predicate;

import static com.googlecode.totallylazy.Sequences.sequence;

@Deprecated
class JsonResponse {

    private final HttpResponse response;

    public JsonResponse(HttpResponse response) {
        if (!isJson(response))
            throw new UnexpectedContentType(response);
        this.response = response;
    }

    private static boolean isJson(HttpResponse response) {
        if (!sequence(response.getHeaders()).find(json()).isEmpty())
            return true;
        return false;
    }

    private static Predicate<Header> json() {
        return header -> header.name().equalsIgnoreCase("content-type") && header.value().contains("application/json");
    }

    public String body() {
        return response.getContent().asString();
    }
}
