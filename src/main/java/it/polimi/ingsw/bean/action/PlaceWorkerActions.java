package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Tile;

public class PlaceWorkerActions extends GameAction {

    private final Tile.IndexTile[] positions;

    public PlaceWorkerActions(Tile.IndexTile[] positions, String nickname) {
        super(nickname);
        if (positions.length != 2) throw new IllegalArgumentException();
        this.positions = positions;
    }

    @Override
    void execute() throws AlreadySetException {
        getPlayer().setWorkers(positions);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.PLACE_WORKERS;
    }
}
