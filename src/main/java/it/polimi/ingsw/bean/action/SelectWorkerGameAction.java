package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class SelectWorkerGameAction extends GameAction {
    private final Tile.IndexTile workerPosition;

    public SelectWorkerGameAction(Tile.IndexTile workerPosition, String nickname) throws IllegalArgumentException {
        super(nickname);
        this.workerPosition = workerPosition;
    }

    @Override
    void setPlayer(Player player) {
        Worker[] workers = player.getWorkers();
        if (!(workerPosition.equals(workers[0].getIndexTile()) || workerPosition.equals(workers[1].getIndexTile()))) {
            throw new IllegalArgumentException();
        }
        this.player = player;
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.SELECT_WORKER;
    }

    @Override
    void execute() {
        getPlayer().getGod().selectWorker(workerPosition);
    }
}
