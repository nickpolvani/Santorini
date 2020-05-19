package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Tile;

public abstract class IndexTileGameAction extends GameAction {
    protected final Tile.IndexTile indexTile;

    public IndexTileGameAction(Tile.IndexTile indexTile, String nickname) {
        super(nickname);
        this.indexTile = indexTile;
    }

    public Tile.IndexTile getIndexTile() {
        return indexTile;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IndexTileGameAction)) return false;
        return super.equals(obj) && indexTile.equals(((IndexTileGameAction) obj).getIndexTile());
    }
}
