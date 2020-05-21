package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

/**
 * This class is the only one capable of performing an action.
 * The only owner of an instance of this class is GameController.
 * The class exists to hide the execute method of Actions in this package,
 * to make sure that the only one capable of performing actions is the GameController.
 *
 * @see Action
 * @see GameController
 */
public class ActionHandler {
    /**
     *
     */
    private final SetupTurn setupTurn;

    /**
     * Default constructor
     *
     * @param setupTurn
     */
    public ActionHandler(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }

    //TODO bisogna cambiare questo metodo.
    // Non è possibile che ogni action del gioco venga controllata come instanza di SetupTurnAction
    // Bisogna cambiare action handle a runtime quando cambia il turno da tipo di setup a tipo di gioco.
    public synchronized void execute(GameAction a, Player player) throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        if (a instanceof SetupTurnAction) {
            ((SetupTurnAction) a).setSetupWorkersTurn(setupTurn);
        }
        a.setPlayer(player);
        a.execute();
    }
}
