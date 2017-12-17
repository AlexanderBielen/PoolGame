package poolgame.helpers;

/**
 * An error generated when the program enters an unknown navigation state
 */
public class UnknownStateException extends Exception {
    /**
     * New instance of UnknownStateException
     *
     * @param message to display
     */
    public UnknownStateException(String message) { super(message); }
}
