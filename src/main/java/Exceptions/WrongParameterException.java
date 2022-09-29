package main.java.Exceptions;

public class WrongParameterException extends Exception {
    /**
     * @param message
     */
    public WrongParameterException(final String message) {
        super(message);
    }
}
