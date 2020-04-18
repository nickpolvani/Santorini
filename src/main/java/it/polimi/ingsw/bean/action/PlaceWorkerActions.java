package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

public class PlaceWorkerActions extends Action {

    private Tile.IndexTile[] positions;

    public PlaceWorkerActions(Player player, Tile.IndexTile[] positions) {
        super(player);
        if (positions.length != 2) throw new IllegalArgumentException();
        this.positions = positions;
    }

    @Override
    void run() throws AlreadyOccupiedException, DomeAlreadyPresentException, AlreadySetException {
        getPlayer().setWorker(positions);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.PLACE_WORKERS;
    }
}
