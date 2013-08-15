package bad.robot.radiate.teamcity;

import com.googlecode.totallylazy.Predicate;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;

public class YmlConfiguration implements TeamCityConfiguration {

    private static final Logger logger = Logger.getLogger(TeamCityConfiguration.class);

    private final Map<String, Object> configuration;

    public YmlConfiguration(YmlConfigurationFile file) throws IOException {
        this.configuration = load(file);
    }

    static TeamCityConfiguration loadOrDefault(TeamCity teamcity) {
        try {
            YmlConfigurationFile file = new YmlConfigurationFile();
            file.initialise(teamcity);
            logger.info("configuration stored in " + file.getPath());
            return new YmlConfiguration(file);
        } catch (Exception e) {
            logger.info("failed to create Yml configuration file, falling back to environment variable based config");
            return new EnvironmentVariableConfiguration();
        }
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
    public Iterable<Project> filter(Iterable<Project> projects) {
        List<String> ids = (List<String>) configuration.get("projects");
        return sequence(projects).filter(by(ids));
    }

    private static Predicate<Project> by(final List<String> ids) {
        return new Predicate<Project>() {
            @Override
            public boolean matches(Project other) {
                return sequence(ids).contains(other.getId());
            }
        };
    }
}
