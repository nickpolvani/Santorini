package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

public class ActionHandler {
    private final SetupTurn setupTurn;

    public ActionHandler(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }

    public synchronized void start(Action a) throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        if (a instanceof SetupAction) {
            ((SetupAction) a).setSetupWorkersTurn(setupTurn);
        }
        a.run();
    }
}
