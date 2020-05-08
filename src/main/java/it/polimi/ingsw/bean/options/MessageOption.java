package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

public class MessageOption extends Options {

    public MessageOption(Player player, MessageType messageType) {
        super(player, messageType);
        this.currentOperation = Operation.SEND_MESSAGE;
    }

    @Override
    public void execute() {

    }
}
