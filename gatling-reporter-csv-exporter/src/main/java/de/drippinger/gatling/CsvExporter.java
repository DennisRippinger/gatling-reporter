package de.drippinger.gatling;

import org.nuxeo.tools.gatling.report.SimulationContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Exports the Simulation Context in a file.
 * The file is a CSV (more like a TSV) located in {@code project.build.directory}.
 */
@SuppressWarnings("unused")
public class CsvExporter implements Exporter {

    @Override
    public void publish(ExporterProperties properties, Consumer<String> infoLogger) {

        for (SimulationContext simulationContext : properties.getSimulations()) {
            try (FileWriter writer = writer(properties, simulationContext)) {
                writer.write(simulationContext.toString());

                infoLogger.accept("Wrote to " + filePath(properties, simulationContext));
            } catch (IOException e) {
                throw new ExporterException("Could not write CSV Report", e);
            }

        }
    }

    private FileWriter writer(ExporterProperties properties, SimulationContext simulationContext) throws IOException {
        return new FileWriter(
            filePath(properties, simulationContext)
        );
    }

    private String filePath(ExporterProperties properties, SimulationContext simulationContext) {
        return properties.getProjectBuildDir() +
            File.separator +
            simulationContext.getSimulationName() +
            ".csv";
    }

}
