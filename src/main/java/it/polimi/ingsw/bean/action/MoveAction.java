package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

public class MoveAction extends Action {

    private final IndexTile indexTile;

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public MoveAction(Player player, IndexTile indexTile) {
        super(player);
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
