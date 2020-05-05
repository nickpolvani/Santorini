package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

/**
 * Used by classes God when the user has to answer a question that changes
 * the logic of the player's turn
 */
public class ChoiceAction extends Action {
    private final boolean confirm;

    ChoiceAction(boolean confirm, Player player) {
        super(player);
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
