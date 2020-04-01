package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Player;

public class ConfirmOptions {
    private final Player player;
    private final String message;

    public ConfirmOptions(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
