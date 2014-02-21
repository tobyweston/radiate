package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import com.googlecode.totallylazy.Predicate;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static bad.robot.radiate.teamcity.Username.username;
import static com.googlecode.totallylazy.Sequences.sequence;

public class YmlConfiguration implements TeamCityConfiguration {

    private final Map<String, Object> configuration;

    public YmlConfiguration(YmlConfigurationFile file) throws IOException {
        this.configuration = load(file);
    }

    static TeamCityConfiguration loadOrCreate(TeamCity teamcity, Observable observable) {
        try {
            YmlConfigurationFile file = new YmlConfigurationFile();
            file.initialise(teamcity);
            observable.notifyObservers(new Information("Configuration stored in " + file.getPath()));
            return new YmlConfiguration(file);
        } catch (Exception e) {
            observable.notifyObservers(new Information("Failed to create Yml configuration file, falling back to use environment variables"));
            return new EnvironmentVariableConfiguration();
        }
    }

    private Map<String, Object> load(File configuration) throws FileNotFoundException {
        return (Map<String, Object>) new Yaml().load(new FileReader(configuration));
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

    @Override
    public Password password() {
        return Password.password((String) configuration.get("password"));
    }

    @Override
    public Username username() {
        return Username.username((String) configuration.get("user"));
    }
}
