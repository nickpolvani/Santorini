package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.turn.setup.SetupTurn;
import it.polimi.ingsw.model.Player;

public abstract class SetupAction extends Action {

    protected SetupTurn setupTurn;

    public SetupAction(Player player) {
        super(player);
    }

    void setSetupWorkersTurn(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }
}
