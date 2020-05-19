# Gatling-Reporter

Gatling Reporter is a maven plugin that parses the Gatling `simulation.log` file and transforms it into reportable data.
You get _basically_ the same data as from an HTML report, but can export the data into a persistent data storage
that allows you to calculate trends and reports over time.

This lib is a fork of [gatling-report](https://github.com/nuxeo/gatling-report).

## Motivation

Gatling creates great reports that visualize the answer times under various load scenarios.
These reports are helpful, but usually, one wants to compare them to previous results.
Unfortunately, to compare them, there exist many competing approaches to storing such data: Graphite, Prometheus,
DynamoDB, MongoDB, RDMS product x, or flat files.
And this is just saving the data, visualize them may also be a challenge.

The assumption of this plugin is, writing a Plugin that fits all use cases is futile.
The intention is to have a plugin that prepares a raw overview and then allows developers to write custom additions
via a ServiceLoader approach.
These can then communicate with whatever DB or service used within an organization.

## Usage

```xml
<plugins>
    <plugin>
        <groupId>io.gatling</groupId>
        <artifactId>gatling-maven-plugin</artifactId>
        <version>${gatling-maven-plugin.version}</version>
    </plugin>

    <plugin>
        <groupId>de.drippinger</groupId>
        <artifactId>gatling-reporter</artifactId>
        <version>${gatling-reporter.version}</version>
        <dependencies>
            <!-- Example -->
            <dependency>
                <groupId>de.drippinger</groupId>
                <artifactId>gatling-reporter-csv-exporter</artifactId>
                <version>${gatling-reporter.version}</version>
            </dependency>
        </dependencies>
    </plugin>

</plugins>
```
The plugin itself only calculates the SimulationContext, additional dependencies that use a
[ServiceLoader](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html) can then extend the plugin and
bring their dependencies.
For example, see

```java
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
    // [...]

}
```
To create your own Exporter create a new project or module within your project with one class like the one above.
