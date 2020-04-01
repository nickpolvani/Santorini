package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Player;

public class ConfirmAction extends Action {
    boolean confirm;

    ConfirmAction(boolean c, Player player) {
        super();
        this.setPlayer(player);
        confirm = c;
    }

    @Override
    void run() {

    }
}
