package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.turn.SetupTurn;

public abstract class SetupTurnAction extends GameAction {

    protected SetupTurn setupTurn;

    public SetupTurnAction(String nickname) {
        super(nickname);
    }

    void setSetupWorkersTurn(SetupTurn setupTurn) {
        this.setupTurn = setupTurn;
    }
}
