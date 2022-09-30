package main.java.Exceptions;

public class OverwriteException extends Exception {
    /**
     * @param message
     *      Message desired to be printed
     */
    public OverwriteException(final String message) {
        super(message);
    }
}
