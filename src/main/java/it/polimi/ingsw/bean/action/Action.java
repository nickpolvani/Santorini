package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public abstract class Action {
    private Player player;
    private Worker worker;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    abstract void run();
}