package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.Operation;

public class MessageOption extends Options {


    public MessageOption(String nickname, String messageType, Operation operation) {
        super(nickname, messageType, operation);
    }

    @Override
    protected void cliExecute(CLI view) {
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(message);
        }
    }

    @Override
    protected void guiExecute(GUI gui) {
        if (this.nickname.equals(gui.getNickname())) {
            gui.showMessage(message);
            gui.noReply();
        }
    }

    @Override
    public String isValid(String userInput) {
        return null; // a MessageOption does not expect any user input, so whatever the user may write is accepted
    }
}
