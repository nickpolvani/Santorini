package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Tile.IndexTile;

public class MoveAction extends Action {

    private IndexTile indexTile;

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public void setIndexTile(IndexTile indexTile) {
        this.indexTile = indexTile;
    }

    @Override
    void run() {
        this.getPlayer().getGod().move(indexTile);
    }
}
