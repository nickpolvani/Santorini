package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;

public abstract class GameAction extends Action {

    protected Player player;

    public GameAction(String nickname) {
        super(nickname);
    }

    public Player getPlayer() {
        return player;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    abstract void execute() throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException;
}