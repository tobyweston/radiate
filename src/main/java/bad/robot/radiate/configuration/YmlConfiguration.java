package bad.robot.radiate.configuration;

import java.io.File;
import java.io.IOException;

import static bad.robot.radiate.Environment.getEnvironmentVariable;

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

    private void initialise(File configuration) {

    }

    @Override
    public String host() {
        return getEnvironmentVariable("teamcity.host");
    }

    @Override
    public Integer port() {
        return Integer.valueOf(getEnvironmentVariable("teamcity.port", "8111"));
    }
}
