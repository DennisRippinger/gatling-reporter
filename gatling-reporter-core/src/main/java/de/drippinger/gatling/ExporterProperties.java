package de.drippinger.gatling;

import org.nuxeo.tools.gatling.report.SimulationContext;

import java.util.Properties;
import java.util.Set;

/**
 * Command Object for exporter data.
 * <br>
 * It contains:
 * <ol>
 *     <li>A set of {@link SimulationContext}s. Each context represents a simulation log. Usually you only have one.</li>
 *     <li>The Maven properties from the maven project object. It contains the variables decplared on {@code <properties>}</li>
 *     <li>Project Build dir as of {@code project.build.directory}</li>
 *     <li>A session key that is intended as an arbitrary string to work as a PK. See documentation for more info on that.</li>
 * </ol>
 */
public class ExporterProperties {

    private final Set<SimulationContext> simulations;

    private final Properties mavenProperties;

    private final String projectBuildDir;

    private final String sessionKey;

    public static ExporterProperties of(Set<SimulationContext> simulations, Properties mavenProperties, String projectBuildDir,
                                        String sessionKey) {
        return new ExporterProperties(simulations, mavenProperties, projectBuildDir, sessionKey);
    }

    private ExporterProperties(Set<SimulationContext> simulations, Properties mavenProperties, String projectBuildDir,
                               String sessionKey) {
        this.simulations = simulations;
        this.mavenProperties = mavenProperties;
        this.projectBuildDir = projectBuildDir;
        this.sessionKey = sessionKey;
    }

    public Set<SimulationContext> getSimulations() {
        return simulations;
    }

    public Properties getMavenProperties() {
        return mavenProperties;
    }

    public String getProjectBuildDir() {
        return projectBuildDir;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
