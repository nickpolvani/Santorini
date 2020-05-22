package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.GameController;
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
    public void execute(GameAction a, Player player) throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        a.setPlayer(player);
        a.execute();
    }
}