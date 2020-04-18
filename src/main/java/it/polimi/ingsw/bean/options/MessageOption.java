package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Player;

public class MessageOption extends Options {

    private final String message; // message that the user will see from the View

    public MessageOption(Player player, String message) {
        super(player);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
