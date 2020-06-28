package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

/**
 * action used to let players choose their worker for the current turn
 */
public class SelectWorkerGameAction extends IndexTileGameAction {

    public SelectWorkerGameAction(Tile.IndexTile workerPosition, String nickname) {
        super(workerPosition, nickname);
    }

    @Override
    void setPlayer(Player player) {
        if (player.getWorkers().stream().noneMatch(w -> w.getIndexTile().equals(indexTile))) {
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
        getPlayer().getGod().selectWorker(indexTile);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SelectWorkerGameAction)) return false;
        return super.equals(obj);
    }
}
