package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Charon;

public class SelectOpponentWorkerGameAction extends GameAction {
    private final Tile.IndexTile workerPosition;

    public SelectOpponentWorkerGameAction(String nickname, Tile.IndexTile workerPosition) {
        super(nickname);
        this.workerPosition = workerPosition;
    }

    @Override
    void execute() {
        ((Charon) getPlayer().getGod()).moveWorker(workerPosition);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.SELECT_OPPONENTS_WORKER;
    }
}
