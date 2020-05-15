package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.utilities.MessageType;

public class MessageOption extends Options {


    public MessageOption(String nickname, String messageType, Operation operation) {
        super(nickname, messageType, operation);
    }

    @Override
    public void execute(View view) {
        if (messageType.contains(MessageType.WIN) || messageType.contains(MessageType.LOST)) {
            if (view.getNickname().equals(this.nickname)) {
                if (messageType.contains(MessageType.WIN)) {
                    view.showMessage("Congratulation, you are the Winner!!!!");
                } else {
                    view.showMessage("Sorry, but you lost the game");
                }
            } else {
                view.showMessage(messageType);
            }
            return;
        }
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType);
        }
    }

    @Override
    public String isValid(String userInput) {
        return null; // a MessageOption does not expect any user input, so whatever the user may write is accepted
    }
}
