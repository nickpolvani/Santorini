package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.utilities.MessageType;

public class MessageOption extends Options {


    public MessageOption(String nickname, String messageType, Operation operation) {
        super(nickname, messageType, operation);
    }

    @Override
    protected void cliExecute(CLI view) {
        if (message.contains(MessageType.WIN) || message.contains(MessageType.LOST)) {
            if (view.getNickname().equals(this.nickname)) {
                if (message.contains(MessageType.WIN)) {
                    view.showMessage("Congratulations, you are the Winner!!!!");
                } else {
                    view.showMessage("Sorry, but you lost the game");
                }
            } else {
                view.showMessage(message);
            }
        } else {
            if (view.getNickname().equals(this.nickname)) {
                view.showMessage(message);
            }
        }
    }

    @Override
    protected void guiExecute(GUI gui) {
        if (message.contains(MessageType.WIN)) {
            gui.notifyWinner(this.nickname);
        } else if (message.contains(MessageType.LOST)) {
            gui.notifyLooser(this.nickname);
        } else if (this.nickname.equals(gui.getNickname())) {
            gui.showMessage(message);
            gui.noReply();
        }
    }

    @Override
    public String isValid(String userInput) {
        return null; // a MessageOption does not expect any user input, so whatever the user may write is accepted
    }
}
