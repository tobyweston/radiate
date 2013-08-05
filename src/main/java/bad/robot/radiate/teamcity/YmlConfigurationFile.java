package bad.robot.radiate.teamcity;

import com.googlecode.totallylazy.Callable1;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class YmlConfigurationFile extends File {

    private final TeamCityConfiguration fallback = new EnvironmentVariableConfiguration();

    public YmlConfigurationFile() {
        super(System.getProperty("user.home") + File.separator + ".radiate" + File.separator + "config.yml");
    }

    void initialise(TeamCity teamcity) throws IOException {
        createFolder();
        boolean created = createNewFile();
        if (created)
            populateConfiguration(teamcity);
    }

    private void populateConfiguration(TeamCity teamcity) throws IOException {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = new HashMap<>();
            data.put("projects", getProjectIds(teamcity));
            data.put("host", fallback.host());
            data.put("port", fallback.port());
            writeStringToFile(this, yaml.dump(data));
        } catch (Exception e) {
            deleteOnExit();
            throw e;
        }
    }

    private List<String> getProjectIds(TeamCity teamcity) {
        Iterable<Project> projects = teamcity.retrieveProjects();
        return sequence(projects).map(projectAsId()).toList();
    }

    private static Callable1<Project, String> projectAsId() {
        return new Callable1<Project, String>() {
            @Override
            public String call(Project project) throws Exception {
                return project.getId();
            }
        };
    }

    private void createFolder() {
        new File(System.getProperty("user.home") + File.separator + ".radiate").mkdirs();
    }
}
