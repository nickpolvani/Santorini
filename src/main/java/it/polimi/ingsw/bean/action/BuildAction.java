package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

public class BuildAction extends Action {
    private final IndexTile indexTile;

    public BuildAction(IndexTile indexTile, Player player) {
        super(player);
        this.indexTile = indexTile;
    }

    public IndexTile getIndexTile() {
        return indexTile;
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.BUILD;
    }


    @Override
    void execute() throws DomeAlreadyPresentException {
        this.getPlayer().getGod().build(this.indexTile);
    }

}
