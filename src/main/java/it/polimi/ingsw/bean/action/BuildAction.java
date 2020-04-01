package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Tile;

public class BuildAction extends Action {
    private Tile.IndexTile indexTile;
    private boolean dome;

    public Tile.IndexTile getIndexTile() {
        return indexTile;
    }

    public void setIndexTile(Tile.IndexTile indexTile) {
        this.indexTile = indexTile;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    @Override
    void run() {
        this.getPlayer().getGod().build(this.indexTile);
    }

}
