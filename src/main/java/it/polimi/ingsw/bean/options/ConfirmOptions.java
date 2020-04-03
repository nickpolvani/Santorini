package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;


/**
 * ConfirmOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ConfirmOptions extends Options {

    private final String message; // message that the user will see from the View


    private final Tile[][] boardClone; // holds the new state of the game.

    public ConfirmOptions(Player player, String message, Tile[][] boardClone) {
        super(player);
        this.message = message;
        this.boardClone = boardClone;
    }

    public String getMessage() {
        return message;
    }


    public Tile[][] getBoardClone() {
        return boardClone;
    }
}
