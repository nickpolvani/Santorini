package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;

import java.util.Arrays;


/**
 * ChooseOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ChooseOptions extends Options {

    private final IslandBoard board;

    public ChooseOptions(String nickname, String message, IslandBoard board) {
        super(nickname, message, Operation.CHOOSE);
        alert = "Please insert [Yes/No] or [Y,N]";
        this.board = board;
    }

    @Override
    public void execute(View view) {
        view.printBoard(board);
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType + "\n" + alert);
        }
    }

    @Override
    public String isValid(String userInput) {
        String[] acceptedInput = {"yes", "no", "y", "n"};
        if (Arrays.stream(acceptedInput).anyMatch(x -> x.equalsIgnoreCase(userInput))) {
            return null;
        } else return alert;

    }
}
