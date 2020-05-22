package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.Tile.IndexTile;

public class MoveGameAction extends IndexTileGameAction {

    public MoveGameAction(IndexTile indexTile, String nickname) {
        super(indexTile, nickname);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.MOVE;
    }

    @Override
    void execute() throws AlreadyOccupiedException {
        this.getPlayer().getGod().move(indexTile);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MoveGameAction)) return false;
        return super.equals(obj);
    }
}
