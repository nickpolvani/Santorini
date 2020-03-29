package it.polimi.ingsw.Bean.Options;
import it.polimi.ingsw.Model.*;

public class GodOptions {
    Player player;
    NameGod[] godsToChoose;
    String message;

    public GodOptions(Player player, NameGod[] godsToChoose, String message) {
        this.player = player;
        this.godsToChoose = godsToChoose;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public NameGod[] getGodsToChoose() {
        return godsToChoose;
    }

    public String getMessage() {
        return message;
    }
}
