package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

/**
 * this action is created by the view when user receives one message (MessageOption).
 */
public class NodAction extends Action {

    public NodAction(Player player) {
        super(player);
    }

    /**
     * The user does not have to do anything, just receive message, it does not change model.
     */
    @Override
    void run() {
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.SEND_MESSAGE;
    }
}
