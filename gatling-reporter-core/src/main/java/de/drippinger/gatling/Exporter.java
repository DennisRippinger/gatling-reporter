package de.drippinger.gatling;

import org.nuxeo.tools.gatling.report.SimulationContext;

import java.util.function.Consumer;

/**
 * This interface is to be implemented in other, maybe internal, maven projects.
 * <br>
 * The implementation will be instantiated via a {@link java.util.ServiceLoader} in the mojo. The implementations
 * shall provide a way to persist the {@link SimulationContext} in an appropriate way. This could be: Storing it as a
 * CSV file, insert it into some form of database, interacting with a webservice, whatever does the job.
 *
 * @author Dennis Rippinger
 */
public interface Exporter {

    /**
     * Executes the publication of the {@link SimulationContext}. The session key can be an arbitrary String provided
     * via a maven configuration. The intention is to configure a key that could be used as a primary key to indicate
     * individual results. It could be the git commit hash or a simple timestamp.
     *
     * @param properties command object containing relevant data for the exporter.
     * @param infoLogger consumer to log on info level. The intention is to avoid dependencies to mavens internal
     *                   dependencies in the exporter jars.
     */
    void publish(ExporterProperties properties, Consumer<String> infoLogger);

    /**
     * Returns the name of the exporter. The Mojo will log the execution of each instance.
     *
     * @return the class name as exporter name.
     */
    default String exporterName() {
        return this.getClass().getName();
    }

}
