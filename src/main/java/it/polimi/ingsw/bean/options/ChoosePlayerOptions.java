package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;

import java.util.Arrays;


/**
 * ChoosePlayerOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ChoosePlayerOptions extends PlayerOptions {

    private final IslandBoard boardClone;

    public ChoosePlayerOptions(Player player, IslandBoard boardClone, MessageType message) {
        super(player, message, Operation.CHOOSE);
        this.boardClone = boardClone;
        alert = "Please insert [Yes/No] or [Y,N]. Doesn't matter if the input has neither uppercase nor lowercase letters.";
    }

    public IslandBoard getBoardClone() {
        return boardClone;
    }

    @Override
    public void execute(View view) {
        view.printBoard(boardClone);
        if (view.getNickname().equals(this.getPlayer().getNickname())) {
            view.showMessage(messageType.getMessage() + alert);
        }
    }

    @Override
    public String isValid(String userInput) {
        String[] acceptedInput = {"yes", "no", "y", "n"};
        if (Arrays.stream(acceptedInput).anyMatch(x -> x.equals(userInput.toLowerCase()))) {
            return null;
        } else return alert;

    }
}
