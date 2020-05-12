package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;


/**
 * ChoosePlayerOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ChoosePlayerOptions extends PlayerOptions {

    private final IslandBoard boardClone; // holds the new state of the game.

    public ChoosePlayerOptions(Player player, IslandBoard boardClone, MessageType message) {
        super(player, message, Operation.CHOOSE);
        this.boardClone = boardClone;
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
    public String isValid(String attribute) {
        return null;
    }
}
