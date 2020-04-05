package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

public class BuildAction extends Action {
    private IndexTile indexTile;
    private boolean dome;

    public BuildAction(IndexTile indexTile, Player player) {
        super(player);
        this.indexTile = indexTile;
    }

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    @Override
    void run() throws DomeAlreadyPresentException {
        this.getPlayer().getGod().build(this.indexTile);
    }

}
