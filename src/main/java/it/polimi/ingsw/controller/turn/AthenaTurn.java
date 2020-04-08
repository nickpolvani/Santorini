package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaTurn extends BasicTurn {
    private boolean canMoveUp;


    public AthenaTurn(GameController gameController, Player currentPlayer, List<Observer<Options>> observerList) {
        super(gameController, currentPlayer, observerList);
        this.canMoveUp = true;
    }

    public boolean canMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
}
