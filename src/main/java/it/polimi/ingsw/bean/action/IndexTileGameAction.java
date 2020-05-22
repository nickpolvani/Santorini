package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Objects;

/**
 * Many action are just the choice of a tile,
 * therefore all these actions extend this abstract class
 *
 * @see GameAction
 */
public abstract class IndexTileGameAction extends GameAction {

    /**
     * The tile's index chosen
     * @see Tile.IndexTile
     */
    protected final Tile.IndexTile indexTile;

    /**
     * Default constructor
     * @param indexTile The tile's index chosen
     * @param nickname The player's nickname who generated the action
     * @see Player
     */
    public IndexTileGameAction(Tile.IndexTile indexTile, String nickname) {
        super(nickname);
        if (indexTile == null) throw new NullPointerException();
        this.indexTile = indexTile;
    }

    public Tile.IndexTile getIndexTile() {
        return indexTile;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IndexTileGameAction)) return false;
        return super.equals(obj) && indexTile.equals(((IndexTileGameAction) obj).getIndexTile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), indexTile);
    }
}
