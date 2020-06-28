package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Charon;

/**
 * action used when player uses Charon power
 */
public class SelectOpponentWorkerGameAction extends IndexTileGameAction {

    public SelectOpponentWorkerGameAction(Tile.IndexTile workerPosition, String nickname) {
        super(workerPosition, nickname);
    }

    @Override
    void execute() {
        ((Charon) getPlayer().getGod()).moveWorker(indexTile);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.SELECT_OPPONENTS_WORKER;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SelectOpponentWorkerGameAction)) return false;
        return super.equals(obj);
    }
}
