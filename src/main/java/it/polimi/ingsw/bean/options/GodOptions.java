package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodNameAndDescription;

public class GodOptions {
    private final Player player;
    private final GodNameAndDescription[] godsToChoose;
    private final String message;

    public GodOptions(Player player, GodNameAndDescription[] godsToChoose, String message) {
        this.player = player;
        this.godsToChoose = godsToChoose;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public GodNameAndDescription[] getGodsToChoose() {
        return godsToChoose;
    }

    public String getMessage() {
        return message;
    }
}
