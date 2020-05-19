package de.drippinger.gatling;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static de.drippinger.gatling.GitRevisionChecker.currentRevision;
import static java.time.ZonedDateTime.now;

/**
 * Write result set to a Dynamo DB table calle {@code gatling_report}.
 * The table shall be created beforehand.
 */
public class DynamoDbExporter implements Exporter {

    @Override
    public void publish(ExporterProperties properties, Consumer<String> infoLogger) {
        GatlingReport report = mapToDynamoTable(properties, getSessionKey(properties));

        dynamoDbMapper().save(report);

        infoLogger.accept("Wrote " + properties.getSimulations().size() + " request stats to DynamoDB");
    }

    private String getSessionKey(ExporterProperties properties) {
        String session;

        if (Objects.isNull(properties.getSessionKey())) {
            session = currentRevision();
        } else {
            session = properties.getSessionKey();
        }
        return session;
    }

    private DynamoDBMapper dynamoDbMapper() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        return new DynamoDBMapper(amazonDynamoDB);
    }

    private GatlingReport mapToDynamoTable(ExporterProperties properties, String session) {
        ReportMapper mapper = Mappers.getMapper(ReportMapper.class);

        GatlingReport result = new GatlingReport();
        result.setCommitId(session);
        result.setCurrentTime(now());
        result.setRequests(mapToRequests(properties, mapper));

        return result;
    }

    private List<GatlingReport.Request> mapToRequests(ExporterProperties properties, ReportMapper mapper) {
        return properties.getSimulations().stream()
            .flatMap(context -> context.getRequests().stream())
            .map(mapper::mapToRequest)
            .collect(Collectors.toList());
    }
}
