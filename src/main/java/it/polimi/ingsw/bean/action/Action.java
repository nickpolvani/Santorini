package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public abstract class Action implements Serializable {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public Action(Player player) {
        this.player = player;
    }


    abstract void run() throws AlreadyOccupiedException, DomeAlreadyPresentException;
}