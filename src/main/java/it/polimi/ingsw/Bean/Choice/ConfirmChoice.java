package it.polimi.ingsw.Bean.Choice;
import it.polimi.ingsw.Model.*;

public class ConfirmChoice {
    Player player;
    Boolean confirm;

    public ConfirmChoice(Player player, Boolean confirm) {
        this.player = player;
        this.confirm = confirm;
    }

    public Player getPlayer() {
        return player;
    }

    public Boolean getConfirm() {
        return confirm;
    }
}
