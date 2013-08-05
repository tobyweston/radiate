package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.configuration.Configuration;
import com.googlecode.totallylazy.Callable1;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.Integer.valueOf;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class YmlConfiguration implements Configuration {

    private final HttpClient http = anApacheClient();

    public YmlConfiguration() throws IOException {
        File configuration = createFileIfRequired("config.yml");
        load(configuration);
    }

    private File createFileIfRequired(String file) throws IOException {
        String home = System.getProperty("user.home");
        new File(home + File.separator + ".radiate").mkdirs();
        File configuration = new File(home + File.separator + ".radiate" + File.separator + file);
        boolean created = configuration.createNewFile();
        if (created)
            initialise(configuration);
        return configuration;
    }

    private void load(File configuration) {

    }

    private void initialise(File configuration) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data = new HashMap<>();
        data.put("projects", getProjectIds());
        data.put("host", host());
        data.put("port", port());
        writeStringToFile(configuration, yaml.dump(data));
    }

    private List<String> getProjectIds() {
        Server server = new Server(host(), port());
        TeamCity teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
        Iterable<Project> projects = teamcity.retrieveProjects();
        return sequence(projects).map(projectAsId()).toList();
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
