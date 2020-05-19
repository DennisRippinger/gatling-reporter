package de.drippinger.gatling;

import java.util.Objects;
import java.util.function.Consumer;

import static de.drippinger.gatling.GitRevisionChecker.currentRevision;

public class DynamoDbExporter implements Exporter {

    @Override
    public void publish(ExporterProperties properties, Consumer<String> infoLogger) {
        String session;

        if (Objects.isNull(properties.getSessionKey())) {
            session = currentRevision();
        } else {
            session = properties.getSessionKey();
        }

        // TODO Add DynamoDB action.
    }
}
