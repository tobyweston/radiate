package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import com.googlecode.totallylazy.Callable1;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Environment.getEnvironmentVariable;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.Integer.valueOf;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class YmlConfiguration implements TeamcityConfiguration {

    private final HttpClient http = anApacheClient();
    private final Map<String, Object> configuration;

    public YmlConfiguration() throws IOException {
        File file = createFileIfRequired("config.yml");
        this.configuration = load(file);
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

    private Map load(File configuration) throws FileNotFoundException {
        return (Map) new Yaml().load(new FileReader(configuration));
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
        TeamCity teamcity = new TeamCity(new Server(this), http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
        Iterable<Project> projects = teamcity.retrieveProjects();
        return sequence(projects).map(projectAsId()).toList();
    }

    @Override
    public String host() {
        String host = getEnvironmentVariable("teamcity.host");
        if (host.startsWith("http"))
            throw new IllegalArgumentException("no need to specify a protocol, just a hostname");
        return host;
    }

    @Override
    public Integer port() {
        return valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }

    @Override
    public List<Project> projects() {
        return (List<Project>) configuration.get("projects");
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
