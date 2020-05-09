package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;


/**
 * ConfirmOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ConfirmOptions extends Options {

    private final IslandBoard boardClone; // holds the new state of the game.

    public ConfirmOptions(Player player, IslandBoard boardClone, MessageType message) {
        super(player, message);
        this.boardClone = boardClone;
        this.currentOperation = Operation.CHOOSE;
    }

    public IslandBoard getBoardClone() {
        return boardClone;
    }

    @Override
    public void execute(View view) {
        view.updateBoard(boardClone);
        view.showMessage(messageType.getMessage());
    }

    @Override
    public boolean isValid(Action currentAction) {
        return false;
    }
}
