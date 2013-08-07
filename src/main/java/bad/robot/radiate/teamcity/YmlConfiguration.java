package bad.robot.radiate.teamcity;

import com.googlecode.totallylazy.Predicate;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;

public class YmlConfiguration implements TeamCityConfiguration {

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
