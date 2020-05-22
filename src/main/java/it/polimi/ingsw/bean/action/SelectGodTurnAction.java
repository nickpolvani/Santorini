package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.SetupGodsTurn;
import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectGodTurnAction extends GameAction {

    protected transient SetupTurn setupTurn;

    private final List<GodDescription> godsChosen;

    public SelectGodTurnAction(List<GodDescription> gods, String nickname) {
        super(nickname);
        godsChosen = new ArrayList<>();
        godsChosen.addAll(gods);
    }

    public List<GodDescription> getGodsChosen() {
        return godsChosen;
    }

    void setSetupWorkersTurn(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }

    @Override
    void execute() {
        ((SetupGodsTurn) setupTurn).handleGodChoice(godsChosen);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.CHOOSE_GOD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectGodTurnAction)) return false;
        if (!super.equals(o)) return false;
        SelectGodTurnAction that = (SelectGodTurnAction) o;
        return super.equals(o) && Objects.equals(setupTurn, that.setupTurn) &&
                Objects.equals(godsChosen, that.godsChosen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), setupTurn, godsChosen);
    }
}