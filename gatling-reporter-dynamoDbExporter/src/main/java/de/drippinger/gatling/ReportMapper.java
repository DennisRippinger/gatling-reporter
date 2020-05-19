package de.drippinger.gatling;

import org.mapstruct.Mapper;
import org.nuxeo.tools.gatling.report.RequestStat;

@Mapper
public interface ReportMapper {

    GatlingReport.Request mapToRequest(RequestStat requestStat);

}
