package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.Tile.IndexTile;

public class MoveGameAction extends GameAction {

    private final IndexTile indexTile;

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public MoveGameAction(IndexTile indexTile, String nickname) {
        super(nickname);
        this.indexTile = indexTile;
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.MOVE;
    }

    @Override
    void execute() throws AlreadyOccupiedException {
        this.getPlayer().getGod().move(indexTile);
    }
}
