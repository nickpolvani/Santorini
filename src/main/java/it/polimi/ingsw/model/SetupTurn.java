package it.polimi.ingsw.model;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observable;

public class SetupTurn extends Observable<Options> implements Turn {
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
}
