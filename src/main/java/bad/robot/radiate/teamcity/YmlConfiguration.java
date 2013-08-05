package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.configuration.Configuration;
import com.googlecode.totallylazy.Callable1;

import java.io.File;
import java.io.IOException;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.Integer.valueOf;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class YmlConfiguration implements Configuration {

    public YmlConfiguration() throws IOException {
        File configuration = initialiseIfRequired();
        load(configuration);
    }

    private File initialiseIfRequired() throws IOException {
        String home = System.getProperty("user.home");
        new File(home + File.separator + ".radiate").mkdirs();
        File configuration = new File(home + File.separator + ".radiate" + File.separator + "config.yml");
        boolean created = configuration.createNewFile();
        if (created)
            initialise(configuration);
        return configuration;
    }

    private void load(File configuration) {

    }

    private void initialise(File configuration) throws IOException {
        HttpClient http = anApacheClient();
        Server server = new Server(host(), port());
        TeamCity teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
        Iterable<Project> projects = teamcity.retrieveProjects();
        writeStringToFile(configuration, sequence(projects).map(projectAsId()).toString());
    }

    @Override
    public String host() {
        return getEnvironmentVariable("teamcity.host");
    }

    @Override
    public Integer port() {
        return valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }

    private static Callable1<Project, String> projectAsId() {
        return new Callable1<Project, String>() {
            @Override
            public String call(Project project) throws Exception {
                return project.getId();
            }
        };
    }
}
