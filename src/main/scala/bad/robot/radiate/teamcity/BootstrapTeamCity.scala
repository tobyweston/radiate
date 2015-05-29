package bad.robot.radiate.teamcity

class BootstrapTeamCityS extends TeamCityS(new BootstrapServerS, new EnvironmentVariableConfigurationS().authorisation, new HttpClientFactoryS().create(new EnvironmentVariableConfigurationS), new JsonProjectsUnmarshallerS, new JsonProjectUnmarshallerS, new JsonBuildUnmarshallerS)