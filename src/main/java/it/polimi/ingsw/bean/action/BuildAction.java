package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

public class BuildAction extends Action {
    private Tile.IndexTile indexTile;

    public BuildAction(Tile.IndexTile indexTile, Player player) {
        super(player);
        this.indexTile = indexTile;
    }


    public Tile.IndexTile getIndexTile() {
        return indexTile;
    }


    @Override
    void run() {
        this.getPlayer().getGod().build(this.indexTile);
    }

}
