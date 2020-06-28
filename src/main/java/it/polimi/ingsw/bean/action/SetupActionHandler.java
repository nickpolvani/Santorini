package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

/**
 * @see ActionHandler
 */
public class SetupActionHandler extends ActionHandler {
    /**
     * the setup turn is passed to action SelectGodTurnAction to perform method handleGodChoice
     */
    private final SetupTurn setupTurn;

    /**
     * Default constructor
     *
     * @param setupTurn instance of setup turn
     */
    public SetupActionHandler(SetupTurn setupTurn) {
        super();
        this.setupTurn = setupTurn;
    }

    @Override
    public synchronized void execute(GameAction a, Player player) throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        ((SelectGodTurnAction) a).setSetupWorkersTurn(setupTurn);
        super.execute(a, player);
    }
}
