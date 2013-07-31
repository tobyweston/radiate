package bad.robot.radiate;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {

    public static URL url(String url) {
        try {
            return new URL(url.replace(" ", "%20"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
