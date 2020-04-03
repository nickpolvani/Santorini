package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.model.Player;

public abstract class Action {
    private Player player;
    public Player getPlayer() {
        return player;
    }

    public Action(Player player) {
        this.player = player;
    }


    abstract void run();
}