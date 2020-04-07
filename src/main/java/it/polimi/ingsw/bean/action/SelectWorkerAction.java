package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class SelectWorkerAction extends Action {
    private final Worker worker;

    public SelectWorkerAction(Player player, Worker worker) throws IllegalArgumentException {
        super(player);
        Worker[] workers = player.getWorker();
        if (!(worker.equals(workers[0]) || worker.equals(workers[1]))) {
            throw new IllegalArgumentException();
        }
        this.worker = worker;
    }

    @Override
    void run() {
        getPlayer().getGod().selectWorker(worker);
    }
}
