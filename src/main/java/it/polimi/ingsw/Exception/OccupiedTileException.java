package it.polimi.ingsw.Exception;

/**
 * Exception used when the card is already occupied
 *
 * @author Juri Sacchetta
 */
public class OccupiedTileException extends Exception {

    /**
     * Class constructor
     */
    public OccupiedTileException() {
        super("The tile is already occupied!");
    }

    /**
     * Overload of the default constructor
     *
     * @param message String containing the message to be inserted in the exception.
     */
    public OccupiedTileException(String message) {
        super(message);
    }
}
