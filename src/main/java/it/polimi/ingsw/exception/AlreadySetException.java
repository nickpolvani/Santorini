package it.polimi.ingsw.exception;

/**
 * Exception used when the card is already occupied
 *
 * @author Francesco Puoti
 */
public class AlreadySetException extends Exception {

    public AlreadySetException() {
        super();
    }

    public AlreadySetException(String message) {
        super(message);
    }
}
