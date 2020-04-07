package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaTurn extends BasicTurn {
    private boolean canMoveUp;


    public AthenaTurn(GameController gameController, Player currentPlayer) {
        super(currentPlayer, gameController);
        this.canMoveUp = true;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
}
