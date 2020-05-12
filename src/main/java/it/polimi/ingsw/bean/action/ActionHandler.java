package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

public class ActionHandler {
    private final SetupTurn setupTurn;

    public ActionHandler(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }

    public synchronized void execute(GameAction a, Player player) throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        if (a instanceof SetupTurnAction) {
            ((SetupTurnAction) a).setSetupWorkersTurn(setupTurn);
        }
        a.setPlayer(player);
        a.execute();
    }
}
