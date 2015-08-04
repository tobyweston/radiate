package bad.robot.radiate.teamcity

import java.lang.Integer.valueOf

import bad.robot.radiate.Environment.getEnvironmentVariable

class BootstrapServer extends Server(getEnvironmentVariable("TEAMCITY_HOST"), valueOf(getEnvironmentVariable("TEAMCITY_PORT", "8111")))