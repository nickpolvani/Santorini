package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.SetupGodsTurn;
import it.polimi.ingsw.model.god.GodDescription;

public class SelectGodTurnAction extends SetupTurnAction {

    private final GodDescription god;

    public SelectGodTurnAction(GodDescription god, String nickname) {
        super(nickname);
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
