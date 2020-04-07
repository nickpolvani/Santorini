package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodNameAndDescription;

public class SelectGodAction extends SetupAction {

    private GodNameAndDescription god;

    public SelectGodAction(Player player, GodNameAndDescription god) {
        super(player);
        this.god = god;
    }


    @Override
    void run() throws AlreadySetException {
        setupTurn.handleGodChoice(god);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE_GOD;
    }
}
