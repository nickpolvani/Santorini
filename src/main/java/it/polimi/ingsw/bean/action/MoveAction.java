package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

public class MoveAction extends Action {

    private IndexTile indexTile;

    public IndexTile getIndexTile() {
        return indexTile;
    }

    public MoveAction(Player player, IndexTile indexTile) {
        super(player);
        this.indexTile = indexTile;
    }

    @Override
    void run() {
        this.getPlayer().getGod().move(indexTile);
    }
}
