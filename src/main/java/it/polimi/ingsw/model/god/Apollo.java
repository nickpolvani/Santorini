package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Apollo extends God {

    /**
     * Default constructor
     */
    Apollo() {
        super(GodNameAndDescription.APOLLO);
    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile t) {
        // TODO implement here
        return null;
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }

    private void force(IndexTile indexTile) {
        // TODO implement here
    }

}