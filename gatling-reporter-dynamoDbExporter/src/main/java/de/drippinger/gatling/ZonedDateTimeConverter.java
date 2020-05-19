package de.drippinger.gatling;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {

    @Override
    public String convert(ZonedDateTime object) {
        return object.format(ISO_DATE_TIME);
    }

    @Override
    public ZonedDateTime unconvert(String object) {
        return ZonedDateTime.parse(object, ISO_DATE_TIME);
    }
}
