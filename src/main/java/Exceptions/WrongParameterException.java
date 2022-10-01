package main.java.Exceptions;

public class WrongParameterException extends Exception {
    /**
     * @param message
     *      Message desired to be printed
     */
    public WrongParameterException(final String message) {
        super(message);
    }
}
