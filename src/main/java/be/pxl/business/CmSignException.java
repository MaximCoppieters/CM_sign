package be.pxl.business;

/**
 * Serves as a wrapper of many exceptions thrown within internals of the API.
 * Makes debugging and error tracing easier.
 */
public class CmSignException extends RuntimeException {
    public CmSignException(String message) {
        super(message);
    }
}
