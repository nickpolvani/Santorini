package it.polimi.ingsw.Exception;

/**
 * Exception used when the card is already occupied
 *
 * @author Francesco Puoti
 */
public class AlreadySetException extends Exception {

    public AlreadySetException(String message) {
        super(message);
    }
}
