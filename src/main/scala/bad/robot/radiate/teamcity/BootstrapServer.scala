package bad.robot.radiate.teamcity

import java.lang.Integer.valueOf

import bad.robot.radiate.EnvironmentS.getEnvironmentVariable

class BootstrapServerS extends ServerS(getEnvironmentVariable("TEAMCITY_HOST"), valueOf(getEnvironmentVariable("TEAMCITY_PORT", "8111")))