package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Tile.IndexTile;

public class BuildGameAction extends IndexTileGameAction {

    public BuildGameAction(IndexTile indexTile, String nickname) {
        super(indexTile, nickname);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.BUILD;
    }


    @Override
    void execute() throws DomeAlreadyPresentException {
        this.getPlayer().getGod().build(this.indexTile);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BuildGameAction)) return false;
        return super.equals(obj);
    }
}
