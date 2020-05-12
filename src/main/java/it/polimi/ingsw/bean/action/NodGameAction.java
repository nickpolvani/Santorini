package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

/**
 * this action is created by the view when user receives one message (MessageOption).
 */
public class NodGameAction extends GameAction {

    public NodGameAction(String nickname) {
        super(nickname);
    }

    /**
     * The user does not have to do anything, just receive message, it does not change model.
     */
    @Override
    void execute() {
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.SEND_MESSAGE;
    }
}
