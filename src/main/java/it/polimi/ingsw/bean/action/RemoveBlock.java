package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Ares;

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
}