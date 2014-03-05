package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.Information;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.defaultString;

class FailedToCreateYmlFile extends Information {

	public FailedToCreateYmlFile(Exception e) {
		super(format("Failed to create Yml configuration file (caused by an %s %s), falling back to use environment variables", e.getClass().getSimpleName(), defaultString(e.getMessage())));
	}
}
