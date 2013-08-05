package bad.robot.radiate.teamcity;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class YmlConfiguration implements TeamcityConfiguration {

    private final Map<String, Object> configuration;

    public YmlConfiguration(YmlConfigurationFile file) throws IOException {
        this.configuration = load(file);
    }

    private Map load(File configuration) throws FileNotFoundException {
        return (Map) new Yaml().load(new FileReader(configuration));
    }

    @Override
    public String host() {
        return (String) configuration.get("host");
    }

    @Override
    public Integer port() {
        return (Integer) configuration.get("port");
    }

    @Override
    public Iterable<Project> projects(TeamCity teamcity) {
        List<String> ids = (List<String>) configuration.get("projects");
        return teamcity.retrieveProjects(ids);
    }

}
