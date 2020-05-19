package de.drippinger.gatling;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.ZonedDateTime;
import java.util.List;

@DynamoDBTable(tableName = "gatling_report")
public class GatlingReport {

    @DynamoDBHashKey
    private String commitId;

    private ZonedDateTime currentTime;

    private List<Request> requests;

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    @DynamoDBTypeConverted( converter = ZonedDateTimeConverter.class )
    public ZonedDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(ZonedDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @DynamoDBDocument
    public static class Request {

        private String request;

        private long count;

        private long successCount;

        private long errorCount;

        private long min;

        private long max;

        private long stddev;

        private long p50;

        private long p90;

        private long p95;

        private long p99;

        private double rps;

        private double avg;

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public long getSuccessCount() {
            return successCount;
        }

        public void setSuccessCount(long successCount) {
            this.successCount = successCount;
        }

        public long getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(long errorCount) {
            this.errorCount = errorCount;
        }

        public long getMin() {
            return min;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        public long getStddev() {
            return stddev;
        }

        public void setStddev(long stddev) {
            this.stddev = stddev;
        }

        public long getP50() {
            return p50;
        }

        public void setP50(long p50) {
            this.p50 = p50;
        }

        public long getP90() {
            return p90;
        }

        public void setP90(long p90) {
            this.p90 = p90;
        }

        public long getP95() {
            return p95;
        }

        public void setP95(long p95) {
            this.p95 = p95;
        }

        public long getP99() {
            return p99;
        }

        public void setP99(long p99) {
            this.p99 = p99;
        }

        public double getRps() {
            return rps;
        }

        public void setRps(double rps) {
            this.rps = rps;
        }

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }
    }


}
