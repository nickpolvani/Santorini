package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.SetupGodsTurn;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.ArrayList;
import java.util.List;

public class SelectGodTurnAction extends SetupTurnAction {

    private final List<GodDescription> godsChosen;

    public SelectGodTurnAction(List<GodDescription> gods, String nickname) {
        super(nickname);
        godsChosen = new ArrayList<>();
        godsChosen.addAll(gods);
    }


    @Override
    void execute() {
        ((SetupGodsTurn) setupTurn).handleGodChoice(godsChosen);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE_GOD;
    }
}
