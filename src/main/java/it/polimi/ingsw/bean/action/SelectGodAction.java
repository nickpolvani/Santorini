package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.SetupGodsTurn;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodDescription;

public class SelectGodAction extends SetupAction {

    private final GodDescription god;

    public SelectGodAction(Player player, GodDescription god) {
        super(player);
        this.god = god;
    }


    @Override
    void execute() {
        ((SetupGodsTurn) setupTurn).handleGodChoice(god);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE_GOD;
    }
}
