package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.AthenaTurn;
import it.polimi.ingsw.controller.turn.BasicTurn;

/**
 * Used by classes God when the user has to answer a question that changes
 * the flow of the player's turn
 *
 * @see BasicTurn
 * @see AthenaTurn
 */
public class ConfirmGameAction extends GameAction {

    /**
     * Boolean representing the player's answer
     */
    private final boolean confirm;

    /**
     * Default constructor
     *
     * @param confirm  Represent the player's answer
     * @param nickname The player's nickname who generate the action
     */
    public ConfirmGameAction(boolean confirm, String nickname) {
        super(nickname);
        this.confirm = confirm;
    }

    /**
     * Records the player's choice using applyChoice method of the god
     */
    @Override
    void execute() {
        getPlayer().getGod().applyChoice(confirm);

    }

    /**
     * @param operation It's the current operation in Turn
     * @return True if the operation passed like parameter is CHOOSE
     */
    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE;
    }


}
