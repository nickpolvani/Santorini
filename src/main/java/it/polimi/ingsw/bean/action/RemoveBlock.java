package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Ares;

/**
 * action used when player uses power of Ares, that removes a block on the selected tile
 */
public class RemoveBlock extends BuildGameAction {
    public RemoveBlock(Tile.IndexTile indexTile, String nickname) {
        super(indexTile, nickname);
    }

    @Override
    void execute() throws DomeAlreadyPresentException {
        if (!(player.getGod() instanceof Ares)) throw new IllegalStateException();
        ((Ares) player.getGod()).removeBlock(indexTile);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.REMOVE_BLOCK;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RemoveBlock)) return false;
        return super.equals(obj);
    }
}