package de.drippinger;

import de.drippinger.gatling.Exporter;
import de.drippinger.gatling.ExporterException;
import de.drippinger.gatling.ExporterProperties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.nuxeo.tools.gatling.report.ParserFactory;
import org.nuxeo.tools.gatling.report.SimulationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * Publishes one or multiple {@link SimulationContext}s.
 * <br>
 * The mojo finds the simulation log in the target directory and creates a {@link SimulationContext} out of it.
 * Depending on the provided folder (see {@link PublishMojo#gatlingReportDir}, it can find multiple simulation logs.
 * The set of logs is then proceeded by {@link Exporter} instances. They are created by the {@link ServiceLoader} pattern.
 * Each Exporter lib has to register its implementation and can, in turn, execute arbitrary code. The lib has to be
 * declared as a plugin dependency in maven.
 */
@Mojo(name = "publish")
public class PublishMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    String projectBuildDir;

    /**
     * By default gatling-report uses the {@code project.build.directory} property and appends /gatling as the
     * directory. Within this folder, it will do file-walk for all {@code simulation.log} files. Use this property to
     * define a specific location within the report folder or a higher location to aggregate multiple simulation files.
     */
    @Parameter(defaultValue = "")
    String gatlingReportDir;

    /**
     * Key under which the name shall be serialized. This is usually a PK.
     */
    @Parameter(defaultValue = "")
    String sessionKey;

    private final Consumer<String> infoLogger = s -> getLog().info(s);

    @Override
    public void execute() {
        getLog().debug("Entering Publish Mojo");
        Set<SimulationContext> simulations = new HashSet<>();

        for (Path simulation : simulations()) {
            simulation(simulation).ifPresent(simulations::add);
        }

        for (Exporter exporter : ServiceLoader.load(Exporter.class)) {
            getLog().info("Executing " + exporter.exporterName());
            publish(simulations, exporter);
        }

    }

    private void publish(Set<SimulationContext> simulations, Exporter exporter) {
        try {
            ExporterProperties properties = ExporterProperties
                .of(simulations, project.getProperties(), projectBuildDir, sessionKey);

            exporter.publish(properties, infoLogger);
        } catch (ExporterException e) {
            getLog().warn(e);
        }
    }

    private Optional<SimulationContext> simulation(Path simulation) {
        try {
            return Optional.of(ParserFactory
                .getParser(simulation.toFile())
                .parse());
        } catch (IOException e) {
            getLog().warn(e);
        }

        return Optional.empty();
    }

    private List<Path> simulations() {
        try (Stream<Path> files = Files.walk(directory())) {
            List<Path> result = files
                .filter(path -> path.toFile().getName().equals("simulation.log"))
                .collect(Collectors.toList());

            getLog().info("Found " + result.size() + " simulation file(s).");

            return result;
        } catch (IOException e) {
            getLog().error(e);
            return Collections.emptyList();
        }
    }

    private Path directory() {
        if (isNull(gatlingReportDir) || Objects.equals(gatlingReportDir, "")) {
            return Paths.get(projectBuildDir + "/gatling");
        } else {
            return Paths.get(gatlingReportDir);
        }
    }

}
