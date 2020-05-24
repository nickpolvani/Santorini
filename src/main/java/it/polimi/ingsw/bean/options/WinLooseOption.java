package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.utilities.MessageType;

public class WinLooseOption extends Options {
    private final IslandBoard board;

    public WinLooseOption(String nickname, String message, IslandBoard board) {
        super(nickname, message, Operation.MESSAGE_NO_REPLY);
        this.board = board;
    }


    @Override
    protected void guiExecute(GUI gui) {
        gui.printBoard(board);
        if (message.contains(MessageType.WIN)) {
            gui.notifyWinner(this.nickname);
        } else if (message.contains(MessageType.LOST)) {
            gui.notifyLooser(this.nickname);
        }
    }

    @Override
    protected void cliExecute(CLI cli) {
        cli.printBoard(board);
        if (cli.getNickname().equals(this.nickname)) {
            if (message.contains(MessageType.WIN)) {
                cli.showMessage("Congratulations, you are the Winner!!!!");
            } else {
                cli.showMessage("Sorry, but you lost the game.");
            }
        } else {
            cli.showMessage(message);
        }
    }

    @Override
    public String isValid(String userInput) {
        return null;
    }
}
