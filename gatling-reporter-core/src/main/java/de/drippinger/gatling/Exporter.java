package de.drippinger.gatling;

import org.nuxeo.tools.gatling.report.SimulationContext;

public interface Exporter {

    void publish(SimulationContext context, String sessionKey);

}
