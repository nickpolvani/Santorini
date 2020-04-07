package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;

public class SetupTurn extends Observable<Options> implements Turn {

    private Player challenger;

    public SetupTurn(Player challenger) {
        this.challenger = challenger;
    }

    //TODO implement setup
    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public void switchTurn() {

    }

    @Override
    public Operation getCurrentOperation() {
        return null;
    }

    @Override
    public void endCurrentOperation() {

    }

    //TODO implement method witch at the end of setup checks if AthenaTurn is necessary
}
