package main.java.Exceptions;

public class InternalErrorException extends Exception {
    /**
     * @param message
     *      Message desired to be printed
     */
    public InternalErrorException(final String message) {
        super(message);
    }
}
