package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
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
    public void cliExecute(CLI cli) {
        cli.printBoard(board);
        if (cli.getNickname().equals(this.nickname)) {
            cli.showMessage(message + "\n" + alert);
        }
    }

    @Override
    protected void guiExecute(GUI gui) {
        gui.printBoard(board);
        if (gui.getNickname().equals(nickname)) {
            gui.showMessage(message);
            gui.choose();
        } else {
            gui.showMessage("Wait while " + this.nickname + " is playing operation: " + this.currentOperation.toString());
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
