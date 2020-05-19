package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.Tile;

import java.util.Arrays;

public class PlaceWorkerActions extends GameAction {

    private final Tile.IndexTile[] positions;

    public PlaceWorkerActions(Tile.IndexTile[] positions, String nickname) {
        super(nickname);
        if (positions.length != 2) throw new IllegalArgumentException();
        this.positions = positions;
    }

    public Tile.IndexTile[] getPositions() {
        return positions;
    }

    @Override
    void execute() throws AlreadySetException {
        getPlayer().setWorkers(positions);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.PLACE_WORKERS;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceWorkerActions)) return false;
        return super.equals(obj) && Arrays.equals(positions, ((PlaceWorkerActions) obj).getPositions());
    }
}
