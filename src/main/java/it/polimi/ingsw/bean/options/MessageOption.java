package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

public class MessageOption extends Options {


    public MessageOption(String nickname, MessageType messageType, Operation operation) {
        super(nickname, messageType, Operation.SEND_MESSAGE);
    }

    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType.getMessage());
        }
    }

    @Override
    public String isValid(String userInput) {
        if (userInput == null) return null;
        throw new IllegalArgumentException();
    }
}
