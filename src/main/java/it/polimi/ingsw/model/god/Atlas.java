package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Atlas extends God {

    /**
     * Default constructor
     */
    protected Atlas() {
        super(GodNameAndDescription.ATLAS);
    }

    @Override
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }


    @Override
    protected Collection<IndexTile> tileToBuild(IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(IndexTile indexTile) {
        super.build(indexTile);
    }

}