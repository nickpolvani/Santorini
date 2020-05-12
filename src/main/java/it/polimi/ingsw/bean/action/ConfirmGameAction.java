package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

/**
 * Used by classes God when the user has to answer a question that changes
 * the logic of the player's turn
 */
public class ConfirmGameAction extends GameAction {
    private final boolean confirm;

    public ConfirmGameAction(boolean confirm, String nickname) {
        super(nickname);
        this.confirm = confirm;
    }

    @Override
    void execute() throws DomeAlreadyPresentException {
        getPlayer().getGod().applyChoice(confirm);

    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE;
    }
}
