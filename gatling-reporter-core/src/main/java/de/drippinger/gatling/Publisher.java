package de.drippinger.gatling;

import org.nuxeo.tools.gatling.report.RequestStat;

public interface Publisher {

    void publish(RequestStat stats);

}
