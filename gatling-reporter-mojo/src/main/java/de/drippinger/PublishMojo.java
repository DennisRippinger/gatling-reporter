package de.drippinger;

import de.drippinger.gatling.Exporter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.nuxeo.tools.gatling.report.ParserFactory;
import org.nuxeo.tools.gatling.report.SimulationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "publish-gatling-report", defaultPhase = LifecyclePhase.NONE)
public class PublishMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}")
    private String projectBuildDir;

    /**
     * Key under which the name shall be serialized. This is usually a PK.
     */
    @Parameter(defaultValue = "", property = "sessionKey")
    private String sessionKey;

    @Override
    public void execute() {
        List<Path> simulations = getSimulations();

        for (Path simulation : simulations) {
            try {
                SimulationContext context = ParserFactory
                    .getParser(simulation.toFile())
                    .parse();

                ServiceLoader<Exporter> services = ServiceLoader.load(Exporter.class);

                services.forEach(exporter -> exporter.publish(context, sessionKey));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private List<Path> getSimulations() {
        System.out.println(projectBuildDir);
        try (Stream<Path> files = Files.walk(Paths.get(projectBuildDir + "/gatling"))) {
            return files.filter(path -> path.toFile().getName().equals("simulation.log"))
                .peek(System.out::println)
                .collect(Collectors.toList());
        } catch (IOException e) {
            getLog().error(e);
            return Collections.emptyList();
        }
    }
}
