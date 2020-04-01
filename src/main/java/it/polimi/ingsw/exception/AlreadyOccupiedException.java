package it.polimi.ingsw.exception;

/**
 * Exception used when the card is already occupied
 *
 * @author Juri Sacchetta
 */
public class AlreadyOccupiedException extends Exception {

    /**
     * Class constructor
     */
    public AlreadyOccupiedException() {
        super("The tile is already occupied!");
    }

    /**
     * Overload of the default constructor
     *
     * @param message String containing the message to be inserted in the exception.
     */
    public AlreadyOccupiedException(String message) {
        super(message);
    }
}
