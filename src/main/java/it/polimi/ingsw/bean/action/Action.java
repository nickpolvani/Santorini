package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public abstract class Action implements Serializable {
    private final Player player;

    public Player getPlayer() {
        return player;
    }

    public Action(Player player) {
        this.player = player;
    }

    abstract void execute() throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException;

    /**
     * @param operation is the current operation in Turn
     * @return true if the operation is compatible with the action
     */
    public abstract Boolean isCompatible(Operation operation);
}