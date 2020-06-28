package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.Operation;

/**
 * Option used to notify user with a text message, this option does not expect any response from the user
 */
public class MessageOption extends Options {


    public MessageOption(String nickname, String messageType) {
        super(nickname, messageType, Operation.MESSAGE_NO_REPLY);
    }

    @Override
    protected void cliExecute(CLI cli) {
        if (cli.getNickname().equals(this.nickname)) {
            cli.showMessage(message);
        }
    }

    @Override
    protected void guiExecute(GUI gui) {
        if (this.nickname.equals(gui.getNickname())) {
            gui.showMessage(message);
            gui.noReply();
        }
    }

    /**
     * @param userInput string given by user in CLI or generated automatically interacting with GUI
     * @return null: a MessageOption does not expect any user input, so whatever the user may write is accepted
     */
    @Override
    public String isValid(String userInput) {
        return null;
    }
}
