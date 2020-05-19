package de.drippinger.gatling;

/**
 * Exception that an Exporter can raise.
 *
 * It will  be handled in the Mojo and displayed accordingly. If one exporter throws such an exception it will only
 * end the execution of this exporter. Other exporter will still be called.
 */
public class ExporterException extends RuntimeException {

    public ExporterException(String message) {
        super(message);
    }

    public ExporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExporterException(Throwable cause) {
        super(cause);
    }

}
