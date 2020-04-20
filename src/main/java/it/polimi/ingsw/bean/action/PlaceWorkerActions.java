package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

public class PlaceWorkerActions extends Action {

    private final Tile.IndexTile[] positions;

    public PlaceWorkerActions(Player player, Tile.IndexTile[] positions) {
        super(player);
        if (positions.length != 2) throw new IllegalArgumentException();
        this.positions = positions;
    }

    @Override
    void run() throws AlreadySetException {
        getPlayer().setWorkers(positions);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.PLACE_WORKERS;
    }
}
