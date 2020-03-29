package it.polimi.ingsw.Bean.Options;
import it.polimi.ingsw.Model.*;

public class ConfirmOptions {
    Player player;
    String message;

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
