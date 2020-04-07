package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

public class BuildAction extends Action {
    private final IndexTile indexTile;
    private final boolean dome;

    public BuildAction(IndexTile indexTile, Player player, boolean dome) {
        super(player);
        this.indexTile = indexTile;
        this.dome = dome;
    }

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public boolean isDome() {
        return dome;
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.BUILD;
    }


    @Override
    void run() throws DomeAlreadyPresentException {
        this.getPlayer().getGod().build(this.indexTile);
    }

}
