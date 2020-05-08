package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;


/**
 * ConfirmOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ConfirmOptions extends Options {

    private final String message; // message that the user will see from the View


    private final IslandBoard boardClone; // holds the new state of the game.

    public ConfirmOptions(Player player, String message, IslandBoard boardClone) {
        super(player);
        this.message = message;
        this.boardClone = boardClone;
        this.currentOperation = Operation.CHOOSE;
    }

    public String getMessage() {
        return message;
    }


    public IslandBoard getBoardClone() {
        return boardClone;
    }
}
