package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

public class MessageOption extends PlayerOptions {

    public MessageOption(Player player, MessageType messageType) {
        super(player, messageType, Operation.SEND_MESSAGE);
    }


    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.getPlayer().getNickname())) {
            view.showMessage(messageType.getMessage());
        }
    }

    @Override
    public String isValid(String userInput) {
        if (userInput == null) return null;
        throw new IllegalArgumentException();
    }
}
