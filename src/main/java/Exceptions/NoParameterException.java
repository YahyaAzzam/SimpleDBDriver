package main.java.Exceptions;

public class NoParameterException extends Exception {
    /**
     * @param message
     *      Message desired to be printed
     */
    public NoParameterException(final String message) {
        super(message);
    }
}
