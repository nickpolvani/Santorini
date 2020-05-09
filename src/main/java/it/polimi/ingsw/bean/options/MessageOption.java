package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

public class MessageOption extends Options {

    public MessageOption(Player player, MessageType messageType) {
        super(player, messageType);
        this.currentOperation = Operation.SEND_MESSAGE;
    }

    @Override
    public void execute(View view) {
        view.showMessage(messageType.getMessage());
    }

    @Override
    public boolean isValid(Action currentAction) {
        return false;
    }
}
